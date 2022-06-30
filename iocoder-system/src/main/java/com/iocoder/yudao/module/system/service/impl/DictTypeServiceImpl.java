package com.iocoder.yudao.module.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iocoder.yudao.module.commons.core.LambdaQueryWrapperX;
import com.iocoder.yudao.module.commons.utils.BeanUtil;
import com.iocoder.yudao.module.system.domain.DictDataDO;
import com.iocoder.yudao.module.system.domain.DictTypeDO;
import com.iocoder.yudao.module.system.mapper.DictDataMapper;
import com.iocoder.yudao.module.system.mapper.DictTypeMapper;
import com.iocoder.yudao.module.system.service.DictTypeService;
import com.iocoder.yudao.module.system.vo.dict.type.DictTypeBatchDeleteReqVO;
import com.iocoder.yudao.module.system.vo.dict.type.DictTypeCreateReqVO;
import com.iocoder.yudao.module.system.vo.dict.type.DictTypeUpdateReqVO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.iocoder.yudao.module.commons.constant.ErrorCodeConstants.DictErrorCode.*;
import static com.iocoder.yudao.module.commons.constant.ErrorCodeConstants.REQ_ARGS_NOT_NULL;
import static com.iocoder.yudao.module.commons.exception.ServiceExceptionUtil.exception;

/**
 * <p>
 * 字典类型表 服务实现类
 * </p>
 *
 * @author wu kai
 * @since 2022-06-30
 */
@Service
public class DictTypeServiceImpl extends ServiceImpl<DictTypeMapper, DictTypeDO> implements DictTypeService {

    @Resource
    DictDataMapper dictDataMapper;

    @Override
    public Long createDictType(DictTypeCreateReqVO createReqVO) {
        // 校验参数
        checkCreateOrUpdate(null, createReqVO.getName(), createReqVO.getType());
        // 校验通过，执行新增操作
        DictTypeDO dictTypeDO = new DictTypeDO();
        BeanUtil.copyProperties(createReqVO, dictTypeDO);
        baseMapper.insert(dictTypeDO);
        return dictTypeDO.getId();
    }

    @Override
    public void updateDictType(DictTypeUpdateReqVO updateReqVO) {
        // 校验参数
        checkCreateOrUpdate(updateReqVO.getId(), updateReqVO.getName(), null);
        // 校验通过，执行编辑操作
        DictTypeDO dictTypeDO = new DictTypeDO();
        BeanUtil.copyProperties(updateReqVO, dictTypeDO);
        baseMapper.updateById(dictTypeDO);
    }

    @Override
    public void deleteDictType(Long dictTypeId) {
        // 校验字典类型是否存在
        DictTypeDO dictTypeDO = checkDictTypeExists(dictTypeId);
        // 校验字典类型下是否存在字典数据
        Long aLong = dictDataMapper.selectCount(new LambdaQueryWrapperX<DictDataDO>()
                .eq(DictDataDO::getDictType, dictTypeDO.getType())
        );
        if (aLong > 0) {
            throw exception(DICT_TYPE_HAS_CHILDREN);
        }
        // 校验通过，执行删除
        baseMapper.deleteById(dictTypeId);


    }

    @Override
    public void deleteDictTypeBatch(DictTypeBatchDeleteReqVO batchDeleteReqVO) {
        if(CollectionUtils.isEmpty(batchDeleteReqVO.getDictTypeIds())){
            throw exception(REQ_ARGS_NOT_NULL);
        }
        batchDeleteReqVO.getDictTypeIds().forEach(this::deleteDictType);
    }

    private void checkCreateOrUpdate(Long id, String name, String type) {
        // 校验是否存在
        checkDictTypeExists(id);
        // 校验字典类型的名字的唯一性
        checkDictTypeNameUnique(id, name);
        // 校验字典类型的类型的唯一性
        checkDictTypeUnique(id, type);
    }

    /**
     * 校验字典类型是否存在
     *
     * @param id 字典类型编号
     * @return 字典类型信息
     */
    public DictTypeDO checkDictTypeExists(Long id) {
        if (id == null) {
            return null;
        }
        DictTypeDO dictType = baseMapper.selectById(id);
        if (ObjectUtils.isEmpty(dictType)) {
            throw exception(DICT_TYPE_NOT_EXISTS);
        }
        return dictType;
    }

    /**
     * 校验字典类型名称是否唯一
     *
     * @param id   字典类型编号
     * @param name 字典类型名称
     */
    public void checkDictTypeNameUnique(Long id, String name) {
        DictTypeDO dictType = baseMapper.selectOne(new LambdaQueryWrapperX<DictTypeDO>()
                .eq(DictTypeDO::getName, name).orderByDesc(DictTypeDO::getCreateTime).last("limit 1")
        );
        if (ObjectUtils.isEmpty(dictType)) {
            return;
        }
        if (!dictType.getId().equals(id)) {
            throw exception(DICT_TYPE_NAME_DUPLICATE);
        }
    }

    /**
     * 检验字典类型的类型是否唯一
     *
     * @param id   字典类型编号
     * @param type 字典类型的类型
     */
    public void checkDictTypeUnique(Long id, String type) {
        if (StrUtil.isEmpty(type)) {
            return;
        }
        DictTypeDO dictType = baseMapper.selectOne(new LambdaQueryWrapperX<DictTypeDO>()
                .eq(DictTypeDO::getType, type).orderByDesc(DictTypeDO::getCreateTime).last("limit 1")
        );
        if (ObjectUtils.isEmpty(dictType)) {
            return;
        }
        if (!dictType.getId().equals(id)) {
            throw exception(DICT_TYPE_TYPE_DUPLICATE);
        }
    }
}
