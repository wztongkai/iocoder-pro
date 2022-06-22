package com.iocoder.yudao.system.mapper;

import com.iocoder.yudao.system.domain.UserRoleDO;
import com.iocoder.yudao.commons.core.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;

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

}