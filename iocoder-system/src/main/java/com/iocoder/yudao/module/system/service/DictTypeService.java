package com.iocoder.yudao.module.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iocoder.yudao.module.system.domain.DictTypeDO;
import com.iocoder.yudao.module.system.vo.dict.type.DictTypeBatchDeleteReqVO;
import com.iocoder.yudao.module.system.vo.dict.type.DictTypeCreateReqVO;
import com.iocoder.yudao.module.system.vo.dict.type.DictTypeUpdateReqVO;

/**
 * <p>
 * 字典类型表 服务类
 * </p>
 *
 * @author wu kai
 * @since 2022-06-30
 */
public interface DictTypeService extends IService<DictTypeDO> {

    /**
     * 创建字典类型
     * @param createReqVO 字典类型信息
     * @return 字典类型编号
     */
    Long createDictType(DictTypeCreateReqVO createReqVO);

    /**
     * 修改字典类型
     * @param updateReqVO 字典类型信息
     */
    void updateDictType(DictTypeUpdateReqVO updateReqVO);

    /**
     * 删除字典类型
     * @param dictTypeId 字典类型编号
     */
    void deleteDictType(Long dictTypeId);

    /**
     * 批量删除字典类型
     * @param batchDeleteReqVO 字典类型编号集合对象
     */
    void deleteDictTypeBatch(DictTypeBatchDeleteReqVO batchDeleteReqVO);
}
