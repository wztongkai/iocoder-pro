package com.iocoder.yudao.module.system.mapper;

import com.iocoder.yudao.module.commons.core.LambdaQueryWrapperX;
import com.iocoder.yudao.module.commons.core.mapper.BaseMapperX;
import com.iocoder.yudao.module.system.domain.PostDO;
import com.iocoder.yudao.module.system.vo.post.PostListReqVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 岗位信息表 Mapper 接口
 * </p>
 *
 * @author wu kai
 * @since 2022-06-22
 */
@Mapper
public interface PostMapper extends BaseMapperX<PostDO> {

    /**
     * 筛选岗位列表
     * @param listReqVO 筛选信息
     * @return 列表信息
     */
    default List<PostDO> getSimplePosts(PostListReqVO listReqVO) {
        return selectList(new LambdaQueryWrapperX<PostDO>()
                .likeIfPresent(PostDO::getName, listReqVO.getName())
                .eqIfPresent(PostDO::getStatus, listReqVO.getStatus())
                .orderByDesc(PostDO::getSort)
        );
    }
}
