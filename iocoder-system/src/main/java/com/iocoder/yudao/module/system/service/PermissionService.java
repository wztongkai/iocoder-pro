package com.iocoder.yudao.module.system.service;

import com.iocoder.yudao.module.commons.core.domain.LoginUser;
import com.iocoder.yudao.module.commons.core.domain.UserDO;
import com.iocoder.yudao.module.system.domain.MenuDO;

import java.util.List;
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
     *
     * @param userId  用户编号
     * @param roleIds 角色编号数组
     */
    void allotUserRole(Long userId, Set<Long> roleIds);

    /**
     * 获取用户角色权限信息
     *
     * @param user 用户信息
     * @return 角色权限信息
     */
    Set<String> getRolePermission(UserDO user);

    /**
     * 获取用户菜单权限信息
     *
     * @param loginUser 用户信息
     * @return 菜单权限信息
     */
    Set<String> getMenuPermission(LoginUser loginUser);

    /**
     * 获取用户角色编号集合（角色状态为已开启的角色）
     *
     * @param loginUser  用户信息
     * @param roleStatus 角色状态（已开启）
     * @return 角色编号集合
     */
    Set<Long> getUserRoleIds(LoginUser loginUser, Set<Integer> roleStatus);

    /**
     * 获取用户菜单列表
     *
     * @param roleIds    角色编号
     * @param menuTypes  菜单类型
     * @param MenuStatus 菜单状态
     * @return 用户已开启菜单列表
     */
    List<MenuDO> getUserMenusList(Set<Long> roleIds, Set<Integer> menuTypes, Set<Integer> MenuStatus);
}
