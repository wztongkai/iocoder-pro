package com.iocoder.yudao.module.system.service;

import com.iocoder.yudao.module.system.domain.RoleDO;
import com.iocoder.yudao.module.system.domain.UserRoleDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户和角色关联表 服务类
 * </p>
 *
 * @author wu kai
 * @since 2022-06-22
 */
public interface UserRoleService extends IService<UserRoleDO> {

    /**
     * 根据用户编号获取用户角色信息
     * @param userId 用户编号
     * @return 角色信息列表
     */
    List<RoleDO> selectRoleInfoByUserId(Long userId);
}
