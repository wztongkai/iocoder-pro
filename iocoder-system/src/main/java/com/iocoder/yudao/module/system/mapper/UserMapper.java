package com.iocoder.yudao.module.system.mapper;

import com.iocoder.yudao.module.commons.core.LambdaQueryWrapperX;
import com.iocoder.yudao.module.commons.core.domain.PageResult;
import com.iocoder.yudao.module.commons.core.domain.UserDO;
import com.iocoder.yudao.module.commons.core.mapper.BaseMapperX;
import com.iocoder.yudao.module.system.vo.user.UserPageQueryRequestVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户信息表 Mapper 接口
 * </p>
 *
 * @author wu kai
 * @since 2022-06-22
 */
@Mapper
public interface UserMapper extends BaseMapperX<UserDO> {

    default PageResult<UserDO> selectUserList(UserPageQueryRequestVo requestVo) {

        return selectPage(requestVo, new LambdaQueryWrapperX<UserDO>()
                .likeIfPresent(UserDO::getUsername, requestVo.getSearch())
                .orIfPresent()
                .likeIfPresent(UserDO::getMobile, requestVo.getSearch())
                .orIfPresent()
                .likeIfPresent(UserDO::getNickname, requestVo.getSearch())
                .orIfPresent()
                .eqIfPresent(UserDO::getEmail, requestVo.getSearch())
                .eqIfPresent(UserDO::getStatus, requestVo.getStatus())
                .betweenIfPresent(UserDO::getCreateTime, requestVo.getCreateTime())
                .orderByDesc(UserDO::getCreateTime)
        );
    }
}
