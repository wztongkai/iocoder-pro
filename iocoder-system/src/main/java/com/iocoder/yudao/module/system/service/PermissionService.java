package com.iocoder.yudao.module.system.service;

import com.iocoder.yudao.module.commons.core.domain.LoginUser;
import com.iocoder.yudao.module.commons.core.domain.UserDO;
import com.iocoder.yudao.module.commons.enums.menu.MenuIdEnum;
import com.iocoder.yudao.module.commons.utils.BeanUtil;
import com.iocoder.yudao.module.commons.utils.convert.CollConvertUtils;
import com.iocoder.yudao.module.system.domain.MenuDO;
import com.iocoder.yudao.module.system.vo.auth.AuthMenuRespVO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.LoggerFactory;

import java.util.*;

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

    /**
     * 将list类型菜单列表转换为树形
     * @param menuList 菜单列表
     * @return 树形菜单列表
     */
    default List<AuthMenuRespVO> buildMenuTree(List<MenuDO> menuList){
        // 对菜单进行排序
        menuList.sort(Comparator.comparing(MenuDO::getSort));
        // 构建菜单树
        Map<Long, AuthMenuRespVO> treeMap = new LinkedHashMap<>();
        menuList.forEach(menu -> {
            AuthMenuRespVO authMenuRespVO = new AuthMenuRespVO();
            BeanUtil.copyProperties(menu,authMenuRespVO);
            treeMap.put(menu.getId(),authMenuRespVO);
        });
        treeMap.values().stream().filter(node -> !Objects.equals(node.getParentId(), MenuIdEnum.ROOT.getId())).forEach(childNode -> {
            AuthMenuRespVO parentNode = treeMap.get(childNode.getParentId());
            if (parentNode == null) {
                LoggerFactory.getLogger(getClass()).error("[buildMenuTree]节点编号为：{}，父节点为：{}",
                        childNode.getId(), childNode.getParentId());
                return;
            }
            // 将自己添加到父节点中
            if (parentNode.getChildren() == null) {
                parentNode.setChildren(new ArrayList<>());
            }
            parentNode.getChildren().add(childNode);
        });
        // 获取所有根节点
        return CollConvertUtils.filterList(treeMap.values(), node -> MenuIdEnum.ROOT.getId().equals(node.getParentId()));
    }
}
