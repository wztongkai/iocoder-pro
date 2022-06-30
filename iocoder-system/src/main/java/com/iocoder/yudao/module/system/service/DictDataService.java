package com.iocoder.yudao.module.system.service;

import com.iocoder.yudao.module.commons.core.domain.PageResult;
import com.iocoder.yudao.module.system.domain.DictDataDO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.iocoder.yudao.module.system.vo.dict.data.*;

import java.util.List;

/**
 * <p>
 * 字典数据表 服务类
 * </p>
 *
 * @author wu kai
 * @since 2022-06-30
 */
public interface DictDataService extends IService<DictDataDO> {

    /**
     * 创建字典数据
     *
     * @param createReqVO 字典数据信息
     * @return 字典数据编号
     */
    Long createDictData(DictDataCreateReqVO createReqVO);

    /**
     * 编辑字典数据
     *
     * @param updateReqVO 字典数据信息
     */
    void updateDictData(DictDataUpdateReqVO updateReqVO);

    /**
     * 删除字典数据
     *
     * @param dictDataId 字典数据编号
     */
    void deleteDictData(Long dictDataId);

    /**
     * 批量删除字典数据
     *
     * @param batchDeleteReqVO 字典数据编号集合对象
     */
    void deleteDictDataBatch(DictDataBatchDeleteReqVO batchDeleteReqVO);

    /**
     * 获得字典数据列表
     *
     * @return 字典数据全列表
     */
    List<DictDataSimpleRespVO> getDictDatas();

    /**
     * 获得字典数据分页列表
     *
     * @param pageReqVO 分页请求
     * @return 字典数据分页列表
     */
    PageResult<DictDataRespVO> getDictDataPage(DictDataPageReqVO pageReqVO);

    /**
     * 查询字典详情
     *
     * @param id 字典编号啊
     * @return 字典详情
     */
    DictDataRespVO getDictData(Long id);
}
