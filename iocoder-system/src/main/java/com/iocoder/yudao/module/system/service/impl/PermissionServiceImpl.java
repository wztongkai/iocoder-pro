package com.iocoder.yudao.module.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.iocoder.yudao.module.commons.core.LambdaQueryWrapperX;
import com.iocoder.yudao.module.commons.core.domain.LoginUser;
import com.iocoder.yudao.module.commons.core.domain.RoleVO;
import com.iocoder.yudao.module.commons.core.domain.UserDO;
import com.iocoder.yudao.module.commons.enums.role.RoleCodeEnum;
import com.iocoder.yudao.module.commons.utils.SecurityUtils;
import com.iocoder.yudao.module.commons.utils.StringUtils;
import com.iocoder.yudao.module.commons.utils.convert.CollConvertUtils;
import com.iocoder.yudao.module.system.domain.MenuDO;
import com.iocoder.yudao.module.system.domain.RoleDO;
import com.iocoder.yudao.module.system.domain.RoleMenuDO;
import com.iocoder.yudao.module.system.domain.UserRoleDO;
import com.iocoder.yudao.module.system.mapper.RoleMenuMapper;
import com.iocoder.yudao.module.system.mapper.UserRoleMapper;
import com.iocoder.yudao.module.system.service.MenuService;
import com.iocoder.yudao.module.system.service.PermissionService;
import com.iocoder.yudao.module.system.service.RoleMenuService;
import com.iocoder.yudao.module.system.service.RoleService;
import com.iocoder.yudao.module.system.vo.permission.role.RoleRespVO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

import static com.iocoder.yudao.module.commons.utils.convert.CollConvertUtils.convertSet;

/**
 * 权限 Service 接口
 *
 * @author wu kai
 * @date 2022/6/27
 */
@Service("ss")
public class PermissionServiceImpl implements PermissionService {

    /**
     * 所有权限标识
     */
    private static final String ALL_PERMISSION = "*:*:*";

    @Resource
    RoleService roleService;

    @Resource
    MenuService menuService;

    @Resource
    RoleMenuMapper roleMenuMapper;

    @Resource
    RoleMenuService roleMenuService;

    @Resource
    UserRoleMapper userRoleMapper;

    @Override
    public Set<Long> getRoleMenuIds(Long roleId) {
        // 判断是否为管理员
        RoleRespVO roleInfo = roleService.getRoleInfo(roleId);
        // 是管理员返回所有菜单
        if (RoleCodeEnum.isSuperAdmin(roleInfo.getCode())) {
            return convertSet(menuService.getMenus(), MenuDO::getId);
        }
        // 不是管理员，返回已拥有的菜单
        return convertSet(roleMenuMapper.selectListByRoleId(roleId), RoleMenuDO::getMenuId);
    }

    @Override
    public void allotRoleMenu(Long roleId, Set<Long> menuIds) {
        // 获得角色拥有的菜单编号
        Set<Long> dbMenuIds = convertSet(roleMenuMapper.selectListByRoleId(roleId),
                RoleMenuDO::getMenuId);
        // 计算新增和删除的菜单编号
        Collection<Long> createMenuIds = CollUtil.subtract(menuIds, dbMenuIds);
        Collection<Long> deleteMenuIds = CollUtil.subtract(dbMenuIds, menuIds);
        // 执行新增操作
        if (CollectionUtil.isNotEmpty(createMenuIds)) {
            createMenuIds.forEach(menuId -> {
                roleMenuMapper.insert(new RoleMenuDO().setMenuId(menuId).setRoleId(roleId));
            });
        }
        // 执行删除操作
        if (CollectionUtils.isNotEmpty(deleteMenuIds)) {
            deleteMenuIds.forEach(menuId -> {
                roleMenuMapper.delete(new LambdaUpdateWrapper<RoleMenuDO>()
                        .eq(RoleMenuDO::getRoleId, roleId)
                        .in(RoleMenuDO::getMenuId, menuId)
                );
            });
        }

    }

    @Override
    public void allotUserRole(Long userId, Set<Long> roleIds) {
        // 获取用户拥有的角色编号
        Set<Long> dbRoleIds = convertSet(userRoleMapper.selectListByUserId(userId), UserRoleDO::getRoleId);
        // 计算新增和删除的菜单编号
        Collection<Long> createRoleIds = CollUtil.subtract(roleIds, dbRoleIds);
        Collection<Long> deleteRoleIds = CollUtil.subtract(dbRoleIds, roleIds);
        // 执行新增操作
        if (CollectionUtil.isNotEmpty(createRoleIds)) {
            createRoleIds.forEach(roleId -> {
                userRoleMapper.insert(new UserRoleDO().setUserId(userId).setRoleId(roleId));
            });
        }
        // 执行删除操作
        if (CollectionUtils.isNotEmpty(deleteRoleIds)) {
            deleteRoleIds.forEach(roleId -> {
                userRoleMapper.delete(new LambdaUpdateWrapper<UserRoleDO>()
                        .eq(UserRoleDO::getRoleId, roleId)
                        .in(UserRoleDO::getUserId, userId)
                );
            });
        }
    }

    @Override
    public Set<String> getRolePermission(UserDO user) {
        Set<String> roles = new HashSet<String>();
        // 管理员拥有所有权限
        if (user.isAdmin()) {
            roles.add("admin");
        } else {
            roles.addAll(roleService.selectRolePermissionByUserId(user.getId()));
        }
        return roles;
    }

    @Override
    public Set<String> getMenuPermission(LoginUser loginUser) {
        List<String> permissions = loginUser.getPermissions();
        Set<String> permsSet = new HashSet<>();
        for (String perm : permissions) {
            if (StringUtils.isNotEmpty(perm)) {
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        return permsSet;
    }

    @Override
    public Set<Long> getUserRoleIds(LoginUser loginUser, Set<Integer> roleStatus) {
        // 获取用户角色列表
        List<RoleVO> roleVoList = loginUser.getRoleVoList();
        // 将角色列表根据角色标号转化为set
        Set<Long> roleIds = convertSet(roleVoList, RoleVO::getId);
        if (CollUtil.isEmpty(roleIds)) {
            return Collections.emptySet();
        }
        // 过滤掉角色信息为空的
        if (CollectionUtil.isNotEmpty(roleStatus)) {
            roleIds.removeIf(roleId -> {
                RoleDO role = roleService.getOne(new LambdaUpdateWrapper<RoleDO>()
                        .eq(RoleDO::getStatus, roleStatus)
                        .orderByDesc(RoleDO::getCreateTime).last("limit 1")
                );
                return role == null || !roleStatus.contains(role.getStatus());
            });
        }
        return roleIds;
    }

    @Override
    public List<MenuDO> getUserMenusList(Set<Long> roleIds, Set<Integer> menuTypes, Set<Integer> MenuStatus) {
        if (CollConvertUtils.isAnyEmpty(roleIds, menuTypes, MenuStatus)) {
            return Collections.emptyList();
        }
        // 判断角色中是否包含超级管理员
        List<RoleDO> roleInfoList = roleService.getRoleInfoList(roleIds);
        if (roleService.hasAnySuperAdmin(roleInfoList)) {
            // 获取所有目录、菜单类型，并已开启的menu
            return menuService.getMenuList(menuTypes, MenuStatus);
        }
        // 获取角色拥有的菜单信息
        List<RoleMenuDO> roleMenuDOS = roleMenuMapper.selectList(new LambdaQueryWrapperX<RoleMenuDO>()
                .inIfPresent(RoleMenuDO::getRoleId, roleIds)
        );
        // 收集菜单编号
        Set<Long> menuIds = convertSet(roleMenuDOS, RoleMenuDO::getMenuId);
        // 获取菜单信息
        return menuService.getSimpleMenuInfos(menuIds, menuTypes, MenuStatus);
    }

    /**
     * 验证用户是否具有操作权限
     *
     * @param hasPermission 权限标识
     * @return 验证结果
     */
    public boolean hasPermission(String hasPermission) {
        if (StringUtils.isEmpty(hasPermission)) {
            return false;
        }
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if (ObjectUtils.isEmpty(loginUser) && CollectionUtils.isEmpty(loginUser.getPermissions())) {
            return false;
        }
        List<String> permissions = loginUser.getPermissions();
        return permissions.contains(ALL_PERMISSION) || permissions.contains(StringUtils.trim(hasPermission));
    }
}
