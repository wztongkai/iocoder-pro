package com.iocoder.yudao.module.system.api.permission.menu;

import com.iocoder.yudao.module.commons.core.domain.CommonResult;
import com.iocoder.yudao.module.commons.enums.common.CommonStatusEnum;
import com.iocoder.yudao.module.commons.utils.BeanUtil;
import com.iocoder.yudao.module.system.service.MenuService;
import com.iocoder.yudao.module.system.vo.permission.menu.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static com.iocoder.yudao.module.commons.core.domain.CommonResult.success;

/**
 * @author wu kai
 * @date 2022/6/27
 */
@Api(tags = "管理后台 - 菜单管理")
@RestController
@RequestMapping("/system/menu")
@Validated
public class MenuController {

    @Resource
    MenuService menuService;

    @PostMapping("/create")
    @ApiOperation("创建菜单")
    public CommonResult<Long> createMenu(@Valid @RequestBody MenuCreateReqVO createReqVO) {
        Long menuId = menuService.createMenu(createReqVO);
        return success(menuId);
    }

    @PutMapping("/update")
    @ApiOperation("编辑菜单")
    public CommonResult<Boolean> updateMenu(@Valid @RequestBody MenuUpdateReqVO updateReqVO) {
        menuService.updateMenu(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除菜单")
    @ApiImplicitParam(name = "id", value = "角色编号", required = true, example = "1024", dataTypeClass = Long.class)
    public CommonResult<Boolean> deleteMenu(@RequestParam("id") Long id) {
        menuService.deleteMenu(id);
        return success(true);
    }

    @GetMapping("/list")
    @ApiOperation(value = "获取菜单列表", notes = "用于【菜单管理】界面")
    public CommonResult<List<MenuRespVO>> getMenus(MenuListReqVO reqVO) {
        List<MenuRespVO> list = menuService.getMenus(reqVO);
        return success(list);
    }

    @GetMapping("/list-all-simple")
    @ApiOperation(value = "获取菜单精简信息列表", notes = "只包含被开启的菜单")
    public CommonResult<List<MenuSimpleRespVO>> getSimpleMenus() {
        // 获得菜单列表，只要开启状态的
        MenuListReqVO reqVO = new MenuListReqVO();
        reqVO.setStatus(CommonStatusEnum.ENABLE.getStatus());
        List<MenuRespVO> list = menuService.getMenus(reqVO);
        List<MenuSimpleRespVO> menuSimpleRespList = new ArrayList<>();
        BeanUtil.copyListProperties(list, menuSimpleRespList, MenuSimpleRespVO.class);
        return success(menuSimpleRespList);
    }

    @GetMapping("/get-menu-info")
    @ApiOperation("获取菜单信息")
    public CommonResult<MenuRespVO> getMenuInfo(Long id) {
        MenuRespVO menuRespVO = menuService.getMenuInfo(id);
        return success(menuRespVO);
    }
}
