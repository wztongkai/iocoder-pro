package com.iocoder.yudao.module.system.service;

import java.util.Set;

/**
 * 权限 Service 接口
 */
public interface PermissionService {

    /**
     * 获得角色拥有的菜单编号
     *
     * @param roleId 角色编号
     * @return 角色拥有的菜单编号 set
     */
    Set<Long> getRoleMenuIds(Long roleId);

    /**
     * 给角色分配菜单
     *
     * @param roleId  角色编号
     * @param menuIds 菜单编号集合
     */
    void allotRoleMenu(Long roleId, Set<Long> menuIds);

    /**
     * 给用户分配角色
     * @param userId 用户编号
     * @param roleIds 角色编号数组
     */
    void allotUserRole(Long userId, Set<Long> roleIds);
}
