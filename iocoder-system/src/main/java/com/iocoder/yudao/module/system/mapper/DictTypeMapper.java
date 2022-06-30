package com.iocoder.yudao.module.system.mapper;

import com.iocoder.yudao.module.commons.core.LambdaQueryWrapperX;
import com.iocoder.yudao.module.commons.core.domain.PageResult;
import com.iocoder.yudao.module.commons.core.mapper.BaseMapperX;
import com.iocoder.yudao.module.system.domain.DictTypeDO;
import com.iocoder.yudao.module.system.vo.dict.type.DictTypePageReqVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 字典类型表 Mapper 接口
 * </p>
 *
 * @author wu kai
 * @since 2022-06-30
 */
@Mapper
public interface DictTypeMapper extends BaseMapperX<DictTypeDO> {

    /**
     * 分页查询字典类型列表
     *
     * @param reqVO 分页请求参数
     * @return 列表
     */
    default PageResult<DictTypeDO> selectDictTypePage(DictTypePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DictTypeDO>()
                .likeIfPresent(DictTypeDO::getName, reqVO.getName())
                .likeIfPresent(DictTypeDO::getType, reqVO.getType())
                .eqIfPresent(DictTypeDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(DictTypeDO::getCreateTime, reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByDesc(DictTypeDO::getCreateTime));
    }
}
