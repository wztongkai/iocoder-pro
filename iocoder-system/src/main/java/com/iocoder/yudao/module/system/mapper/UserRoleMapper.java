package com.iocoder.yudao.module.system.mapper;

import com.iocoder.yudao.module.commons.core.LambdaQueryWrapperX;
import com.iocoder.yudao.module.commons.core.mapper.BaseMapperX;
import com.iocoder.yudao.module.system.domain.UserRoleDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 用户和角色关联表 Mapper 接口
 * </p>
 *
 * @author wu kai
 * @since 2022-06-22
 */
@Mapper
public interface UserRoleMapper extends BaseMapperX<UserRoleDO> {

    /**
     * 根据用户编号获取对应的角色
     * @param userId 用户编号
     * @return 角色
     */
    default List<UserRoleDO> selectListByUserId(Long userId) {
        return selectList(new LambdaQueryWrapperX<UserRoleDO>()
                .eq(UserRoleDO::getUserId ,userId)
        );
    }
}
