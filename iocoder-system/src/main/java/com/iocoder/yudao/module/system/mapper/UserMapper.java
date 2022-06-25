package com.iocoder.yudao.module.system.mapper;

import com.iocoder.yudao.module.commons.core.LambdaQueryWrapperX;
import com.iocoder.yudao.module.commons.core.domain.PageResult;
import com.iocoder.yudao.module.commons.core.domain.UserDO;
import com.iocoder.yudao.module.commons.core.mapper.BaseMapperX;
import com.iocoder.yudao.module.system.vo.user.UserPageQueryRequestVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.Set;

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
                .likeIfPresent(UserDO::getUsername, requestVo.getUsername())
                .likeIfPresent(UserDO::getMobile, requestVo.getMobile())
                .eqIfPresent(UserDO::getStatus, requestVo.getStatus())
                .betweenIfPresent(UserDO::getCreateTime, requestVo.getBeginTime(), requestVo.getEndTime())
                .orderByDesc(UserDO::getCreateTime)
        );
    }
}
