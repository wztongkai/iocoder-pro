package com.iocoder.yudao.module.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iocoder.yudao.module.commons.core.LambdaQueryWrapperX;
import com.iocoder.yudao.module.commons.enums.menu.MenuIdEnum;
import com.iocoder.yudao.module.commons.enums.menu.MenuTypeEnum;
import com.iocoder.yudao.module.commons.exception.ServiceExceptionUtil;
import com.iocoder.yudao.module.commons.utils.BeanUtil;
import com.iocoder.yudao.module.commons.utils.StringUtils;
import com.iocoder.yudao.module.system.domain.MenuDO;
import com.iocoder.yudao.module.system.domain.RoleMenuDO;
import com.iocoder.yudao.module.system.mapper.MenuMapper;
import com.iocoder.yudao.module.system.service.MenuService;
import com.iocoder.yudao.module.system.service.RoleMenuService;
import com.iocoder.yudao.module.system.vo.permission.menu.MenuCreateReqVO;
import com.iocoder.yudao.module.system.vo.permission.menu.MenuListReqVO;
import com.iocoder.yudao.module.system.vo.permission.menu.MenuRespVO;
import com.iocoder.yudao.module.system.vo.permission.menu.MenuUpdateReqVO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

import static com.iocoder.yudao.module.commons.constant.ErrorCodeConstants.MenuErrorCode.*;
import static com.iocoder.yudao.module.commons.exception.ServiceExceptionUtil.exception;

/**
 * <p>
 * 菜单权限表 服务实现类
 * </p>
 *
 * @author wu kai
 * @since 2022-06-27
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, MenuDO> implements MenuService {

    @Resource
    RoleMenuService roleMenuService;

    @Override
    public Long createMenu(MenuCreateReqVO createReqVO) {
        // 校验父菜单
        checkParentResource(createReqVO.getParentId(), null);
        // 校验当前菜单
        checkCurrentResource(createReqVO.getParentId(), createReqVO.getName(), null);
        // 校验完成，执行新增
        MenuDO menuDO = new MenuDO();
        BeanUtil.copyProperties(createReqVO, menuDO);
        // 如果菜单为按钮级，下面属性置空
        if (MenuTypeEnum.BUTTON.getType().equals(menuDO.getType())) {
            menuDO.setComponent("");
            menuDO.setIcon("");
            menuDO.setPath("");
        }
        baseMapper.insert(menuDO);
        return menuDO.getId();
    }

    @Override
    public void updateMenu(MenuUpdateReqVO updateReqVO) {
        // 判断要更新的菜单是否存在
        if (ObjectUtils.isEmpty(baseMapper.selectById(updateReqVO.getId()))) {
            throw exception(MENU_NOT_EXISTS);
        }
        // 校验父菜单存在
        checkParentResource(updateReqVO.getParentId(), updateReqVO.getId());
        // 校验当前菜单
        checkCurrentResource(updateReqVO.getParentId(), updateReqVO.getName(), updateReqVO.getId());
        // 校验完成，执行更新
        MenuDO menu = new MenuDO();
        BeanUtil.copyProperties(updateReqVO, menu);
        baseMapper.updateById(menu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMenu(Long menuId) {
        // 判断菜单是否存在
        if (ObjectUtils.isEmpty(baseMapper.selectById(menuId))) {
            throw exception(MENU_NOT_EXISTS);
        }
        // 判断要删除的菜单下是否存在子菜单
        Long aLong = baseMapper.selectCount(new LambdaQueryWrapperX<MenuDO>()
                .inIfPresent(MenuDO::getId, menuId)
        );
        if (aLong > 0) {
            throw exception(MENU_EXISTS_CHILDREN);
        }
        // 校验通过，删除菜单
        baseMapper.deleteById(menuId);
        // 删除角色的权限
        roleMenuService.remove(new LambdaUpdateWrapper<RoleMenuDO>()
                .eq(RoleMenuDO::getMenuId, menuId)
        );
    }

    @Override
    public List<MenuRespVO> getMenus(MenuListReqVO reqVO) {
        List<MenuDO> menuList = baseMapper.selectList(reqVO);
        List<MenuRespVO> menuRespList = new ArrayList<>();
        BeanUtil.copyListProperties(menuList, menuRespList, MenuRespVO.class);
        return menuRespList;
    }

    @Override
    public List<MenuDO> getMenus() {
        return baseMapper.selectList();
    }

    @Override
    public MenuRespVO getMenuInfo(Long menuId) {
        MenuDO menuDO = baseMapper.selectById(menuId);
        if (ObjectUtils.isEmpty(menuDO)) {
            throw exception(MENU_NOT_EXISTS);
        }
        MenuRespVO menuRespVO = new MenuRespVO();
        BeanUtil.copyProperties(menuDO, menuRespVO);
        return menuRespVO;
    }

    @Override
    public List<String> selectMenuPermByUserId(Long userId) {
        List<String> permission = baseMapper.selectMenuPermsByUserId(userId);
        List<String> permsSet = new ArrayList<>();
        for (String perm : permission) {
            if (StringUtils.isNotEmpty(perm)) {
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        return permsSet;
    }

    @Override
    public List<MenuDO> getMenuList(Set<Integer> menuTypes, Set<Integer> menuStatus) {
        return baseMapper.selectList(new LambdaQueryWrapperX<MenuDO>()
                .inIfPresent(MenuDO::getType,menuTypes)
                .inIfPresent(MenuDO::getStatus,menuStatus)
        );
    }

    @Override
    public List<MenuDO> getSimpleMenuInfos(Set<Long> menuIds, Set<Integer> menuTypes, Set<Integer> menuStatus) {
        List<MenuDO> menuList = baseMapper.selectList(new LambdaQueryWrapperX<MenuDO>()
                .inIfPresent(MenuDO::getId, menuIds)
                .inIfPresent(MenuDO::getType,menuTypes)
                .inIfPresent(MenuDO::getStatus,menuStatus)
        );
        if (CollectionUtils.isEmpty(menuList)) {
            return Collections.emptyList();
        }
        return menuList;
    }

    /**
     * 校验同一个父菜单下，是否存在相同的菜单名
     *
     * @param parentId  父菜单编号
     * @param name      菜单名称
     * @param currentId 当前菜单编号
     */
    public void checkCurrentResource(Long parentId, String name, Long currentId) {
        MenuDO menuDO = baseMapper.selectOne(new LambdaQueryWrapperX<MenuDO>()
                .eqIfPresent(MenuDO::getParentId, parentId)
                .eq(MenuDO::getName, name)
                .orderByDesc(MenuDO::getCreateTime)
                .last("limit 1")
        );
        if (ObjectUtils.isEmpty(menuDO)) {
            return;
        }
        if (menuDO.getId().equals(currentId)) {
            throw ServiceExceptionUtil.exception(MENU_NAME_DUPLICATE);
        }

    }

    /**
     * 校验父菜单
     *
     * @param parentId  父菜单编号
     * @param currentId 单前菜单编号
     */
    public void checkParentResource(Long parentId, Long currentId) {
        if (parentId == null || MenuIdEnum.ROOT.getId().equals(parentId)) {
            return;
        }
        // 不能将本身设置为父菜单
        if (parentId.equals(currentId)) {
            throw exception(MENU_PARENT_ERROR);
        }
        // 查询菜单信息
        MenuDO menu = baseMapper.selectById(parentId);
        // 父菜单不能为空
        if (ObjectUtils.isEmpty(menu)) {
            throw exception(MENU_PARENT_NOT_EXISTS);
        }
        // 父菜单必须是目录或者菜单
        if (!MenuTypeEnum.DIR.getType().equals(menu.getType())
                && !MenuTypeEnum.MENU.getType().equals(menu.getType())) {
            throw ServiceExceptionUtil.exception(MENU_PARENT_NOT_DIR_OR_MENU);
        }

    }
}
