package com.iocoder.yudao.module.system.mapper;

import com.iocoder.yudao.module.commons.core.domain.UserDO;
import com.iocoder.yudao.module.commons.core.mapper.BaseMapperX;
import com.iocoder.yudao.module.system.vo.user.UserPageQueryRequestVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

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

//        default PageResult<UserDO> selectUserList(UserPageQueryRequestVo requestVo, Collection<Long> deptIds) {
//
//        return selectPage(requestVo, new LambdaQueryWrapperX<UserDO>()
//                .likeIfPresent(UserDO::getUsername, requestVo.getSearch())
//                .orIfPresent()
//                .likeIfPresent(UserDO::getMobile, requestVo.getSearch())
//                .orIfPresent()
//                .likeIfPresent(UserDO::getNickname, requestVo.getSearch())
//                .orIfPresent()
//                .likeIfPresent(UserDO::getEmail, requestVo.getSearch())
//                .eqIfPresent(UserDO::getStatus, requestVo.getStatus())
//                .betweenIfPresent(UserDO::getCreateTime, requestVo.getCreateTime())
//                .orderByAsc(UserDO::getId)
//                .orderByDesc(UserDO::getCreateTime)
//        );
//    }
    List<UserDO> selectUserList(@Param("us") UserPageQueryRequestVo requestVo, @Param("deptIds") Collection<Long> deptIds);

}
