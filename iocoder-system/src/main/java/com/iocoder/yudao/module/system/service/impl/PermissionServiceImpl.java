package com.iocoder.yudao.module.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.iocoder.yudao.module.commons.enums.role.RoleCodeEnum;
import com.iocoder.yudao.module.system.domain.MenuDO;
import com.iocoder.yudao.module.system.domain.RoleMenuDO;
import com.iocoder.yudao.module.system.domain.UserRoleDO;
import com.iocoder.yudao.module.system.mapper.RoleMenuMapper;
import com.iocoder.yudao.module.system.mapper.UserRoleMapper;
import com.iocoder.yudao.module.system.service.MenuService;
import com.iocoder.yudao.module.system.service.PermissionService;
import com.iocoder.yudao.module.system.service.RoleService;
import com.iocoder.yudao.module.system.vo.permission.role.RoleRespVO;
import io.swagger.annotations.Api;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Set;

import static com.iocoder.yudao.module.commons.utils.convert.CollConvertUtils.convertSet;

/**
 * 权限 Service 接口
 *
 * @author wu kai
 * @date 2022/6/27
 */
@Api(tags = "管理后台 - 权限")
@RestController
@RequestMapping("/system/permission")
public class PermissionServiceImpl implements PermissionService {
    @Resource
    RoleService roleService;

    @Resource
    MenuService menuService;

    @Resource
    RoleMenuMapper roleMenuMapper;

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
}
