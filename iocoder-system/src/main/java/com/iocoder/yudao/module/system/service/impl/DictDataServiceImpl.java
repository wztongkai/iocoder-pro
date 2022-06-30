package com.iocoder.yudao.module.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iocoder.yudao.module.commons.core.LambdaQueryWrapperX;
import com.iocoder.yudao.module.commons.core.domain.PageResult;
import com.iocoder.yudao.module.commons.enums.common.CommonStatusEnum;
import com.iocoder.yudao.module.commons.utils.BeanUtil;
import com.iocoder.yudao.module.system.domain.DictDataDO;
import com.iocoder.yudao.module.system.domain.DictTypeDO;
import com.iocoder.yudao.module.system.mapper.DictDataMapper;
import com.iocoder.yudao.module.system.service.DictDataService;
import com.iocoder.yudao.module.system.service.DictTypeService;
import com.iocoder.yudao.module.system.vo.dict.data.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.iocoder.yudao.module.commons.constant.ErrorCodeConstants.DictErrorCode.*;
import static com.iocoder.yudao.module.commons.constant.ErrorCodeConstants.REQ_ARGS_NOT_NULL;
import static com.iocoder.yudao.module.commons.exception.ServiceExceptionUtil.exception;

/**
 * <p>
 * 字典数据表 服务实现类
 * </p>
 *
 * @author wu kai
 * @since 2022-06-30
 */
@Service
public class DictDataServiceImpl extends ServiceImpl<DictDataMapper, DictDataDO> implements DictDataService {

    @Resource
    DictTypeService dictTypeService;

    @Override
    public Long createDictData(DictDataCreateReqVO createReqVO) {
        // 校验数据
        checkCreateOrUpdate(null, createReqVO.getValue(), createReqVO.getDictType());
        // 校验通过，执行新增操作
        DictDataDO dictDataDO = new DictDataDO();
        BeanUtil.copyProperties(createReqVO, dictDataDO);
        baseMapper.insert(dictDataDO);
        return dictDataDO.getId();
    }

    @Override
    public void updateDictData(DictDataUpdateReqVO updateReqVO) {
        // 校验数据
        checkCreateOrUpdate(updateReqVO.getId(), updateReqVO.getValue(), updateReqVO.getDictType());
        // 校验通过，执行编辑操作
        DictDataDO dictDataDO = new DictDataDO();
        BeanUtil.copyProperties(updateReqVO, dictDataDO);
        baseMapper.updateById(dictDataDO);
    }

    @Override
    public void deleteDictData(Long dictDataId) {
        // 校验是否存在
        checkDictDataExists(dictDataId);
        // 校验通过，执行删除操作
        baseMapper.deleteById(dictDataId);

    }

    @Override
    public void deleteDictDataBatch(DictDataBatchDeleteReqVO batchDeleteReqVO) {
        if (CollectionUtils.isEmpty(batchDeleteReqVO.getDictDataIds())) {
            throw exception(REQ_ARGS_NOT_NULL);
        }
        batchDeleteReqVO.getDictDataIds().forEach(this::deleteDictData);
    }

    @Override
    public List<DictDataSimpleRespVO> getDictDatas() {
        List<DictDataDO> list = baseMapper.selectList();
        list.sort(Comparator.comparing(DictDataDO::getDictType).thenComparing(DictDataDO::getSort));
        List<DictDataSimpleRespVO> voArrayList = new ArrayList<>();
        BeanUtil.copyListProperties(list, voArrayList, DictDataSimpleRespVO.class);
        return voArrayList;
    }

    @Override
    public PageResult<DictDataRespVO> getDictDataPage(DictDataPageReqVO pageReqVO) {
        PageResult<DictDataDO> pageResult = baseMapper.selectDictDataPage(pageReqVO);
        List<DictDataRespVO> voArrayList = new ArrayList<>();
        BeanUtil.copyListProperties(pageResult.getList(), voArrayList, DictDataRespVO.class);
        return new PageResult<>(voArrayList, pageResult.getTotal());
    }

    @Override
    public DictDataRespVO getDictData(Long id) {
        // 校验是否存在
        checkDictDataExists(id);
        DictDataDO dictDataDO = baseMapper.selectById(id);
        DictDataRespVO dictDataRespVO = new DictDataRespVO();
        BeanUtil.copyProperties(dictDataDO, dictDataRespVO);
        return dictDataRespVO;
    }

    /**
     * 校验数据
     *
     * @param id       字典编号
     * @param value    字典值
     * @param dictType 字典类型
     */
    private void checkCreateOrUpdate(Long id, String value, String dictType) {
        // 校验是否存在
        checkDictDataExists(id);
        // 校验字典类型有效
        checkDictTypeValid(dictType);
        // 校验字典数据的值的唯一性
        checkDictDataValueUnique(id, dictType, value);
    }

    /**
     * 校验字典数据是否存在
     *
     * @param id 字典编号
     */
    public void checkDictDataExists(Long id) {
        if (id == null) {
            return;
        }
        DictDataDO dictData = baseMapper.selectById(id);
        if (ObjectUtils.isEmpty(dictData)) {
            throw exception(DICT_DATA_NOT_EXISTS);
        }
    }

    /**
     * 校验字典类型有效性
     *
     * @param type 字典类型
     */
    public void checkDictTypeValid(String type) {
        DictTypeDO dictType = dictTypeService.getOne(new LambdaQueryWrapperX<DictTypeDO>()
                .eq(DictTypeDO::getType, type)
                .orderByDesc(DictTypeDO::getCreateTime).last("limit 1")
        );
        // 校验字典类型是否存在
        if (ObjectUtils.isEmpty(dictType)) {
            throw exception(DICT_TYPE_NOT_EXISTS);
        }
        // 校验字典类型是否开启
        if (!CommonStatusEnum.ENABLE.getStatus().equals(dictType.getStatus())) {
            throw exception(DICT_TYPE_NOT_ENABLE);
        }
    }

    /**
     * 校验字典数据值是否唯一
     *
     * @param id       字典数据编号
     * @param dictType 字典类型
     * @param value    字典数据值
     */
    public void checkDictDataValueUnique(Long id, String dictType, String value) {
        DictDataDO dictData = baseMapper.selectOne(new LambdaQueryWrapperX<DictDataDO>()
                .eqIfPresent(DictDataDO::getDictType, dictType)
                .eqIfPresent(DictDataDO::getValue, value)
                .orderByDesc(DictDataDO::getCreateTime).last("limit 1")
        );
        if (ObjectUtils.isEmpty(dictData)) {
            return;
        }
        if (!dictData.getId().equals(id)) {
            throw exception(DICT_DATA_VALUE_DUPLICATE);
        }
    }
}
