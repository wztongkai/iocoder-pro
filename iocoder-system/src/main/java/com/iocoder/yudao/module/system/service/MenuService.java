package com.iocoder.yudao.module.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iocoder.yudao.module.system.domain.MenuDO;
import com.iocoder.yudao.module.system.vo.permission.menu.MenuCreateReqVO;
import com.iocoder.yudao.module.system.vo.permission.menu.MenuListReqVO;
import com.iocoder.yudao.module.system.vo.permission.menu.MenuRespVO;
import com.iocoder.yudao.module.system.vo.permission.menu.MenuUpdateReqVO;

import java.util.List;

/**
 * <p>
 * 菜单权限表 服务类
 * </p>
 *
 * @author wu kai
 * @since 2022-06-27
 */
public interface MenuService extends IService<MenuDO> {

    /**
     * 新建菜单
     *
     * @param createReqVO 菜单信息
     * @return 菜单编号
     */
    Long createMenu(MenuCreateReqVO createReqVO);

    /**
     * 编辑菜单
     *
     * @param updateReqVO 菜单信息
     */
    void updateMenu(MenuUpdateReqVO updateReqVO);

    /**
     * 删除菜单
     *
     * @param menuId 菜单编号
     */
    void deleteMenu(Long menuId);

    /**
     * 获取菜单列表
     *
     * @param reqVO 菜单列表信息
     * @return 列表
     */
    List<MenuRespVO> getMenus(MenuListReqVO reqVO);

    /**
     * 获得所有菜单列表
     *
     * @return 菜单列表
     */
    List<MenuDO> getMenus();

    /**
     * 获取菜单详情
     *
     * @param menuId 菜单编号
     * @return 菜单详情
     */
    MenuRespVO getMenuInfo(Long menuId);

    /**
     * 根据用户编号查询权限
     * @param userId 用户编号
     * @return 权限列表
     */
    List<String> selectMenuPermByUserId(Long userId);
}
