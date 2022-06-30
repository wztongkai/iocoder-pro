package com.iocoder.yudao.module.system.mapper;

import com.iocoder.yudao.module.commons.core.LambdaQueryWrapperX;
import com.iocoder.yudao.module.commons.core.domain.PageResult;
import com.iocoder.yudao.module.commons.core.mapper.BaseMapperX;
import com.iocoder.yudao.module.system.domain.DictDataDO;
import com.iocoder.yudao.module.system.vo.dict.data.DictDataPageReqVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Arrays;

/**
 * <p>
 * 字典数据表 Mapper 接口
 * </p>
 *
 * @author wu kai
 * @since 2022-06-30
 */
@Mapper
public interface DictDataMapper extends BaseMapperX<DictDataDO> {

    /**
     * 获得字典数据分页列表
     *
     * @param pageReqVO 分页请求
     * @return 字典数据分页列表
     */
    default PageResult<DictDataDO> selectDictDataPage(DictDataPageReqVO pageReqVO) {
        return selectPage(pageReqVO, new LambdaQueryWrapperX<DictDataDO>()
                .likeIfPresent(DictDataDO::getLabel, pageReqVO.getLabel())
                .likeIfPresent(DictDataDO::getDictType, pageReqVO.getDictType())
                .eqIfPresent(DictDataDO::getStatus, pageReqVO.getStatus())
                .orderByDesc(Arrays.asList(DictDataDO::getDictType, DictDataDO::getSort)));
    }
}
