package com.iocoder.yudao.module.system.api.permission;

import com.iocoder.yudao.module.commons.core.domain.CommonResult;
import com.iocoder.yudao.module.system.service.PermissionService;
import com.iocoder.yudao.module.system.vo.permission.PermissionAllotRoleMenuReqVO;
import com.iocoder.yudao.module.system.vo.permission.PermissionAllotUserRoleReqVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Set;

import static com.iocoder.yudao.module.commons.core.domain.CommonResult.success;

/**
 * 权限 controller 接口
 *
 * @author wu kai
 * @date 2022/6/27
 */
@Api(tags = "管理后台 - 权限")
@RestController
@RequestMapping("/system/permission")
public class PermissionController {

    @Resource
    PermissionService permissionService;

    @ApiOperation("获得角色拥有的菜单编号")
    @ApiImplicitParam(name = "roleId", value = "角色编号", required = true, dataTypeClass = Long.class)
    @GetMapping("/list-role-resources")
    public CommonResult<Set<Long>> listRoleMenus(Long roleId) {
        return success(permissionService.getRoleMenuIds(roleId));
    }

    @PostMapping("/allot-role-menu")
    @ApiOperation("给角色分配菜单")
    public CommonResult<Boolean> assignRoleMenu(@Validated @RequestBody PermissionAllotRoleMenuReqVO reqVO) {
        // 给角色分配菜单
        permissionService.allotRoleMenu(reqVO.getRoleId(), reqVO.getMenuIds());
        return success(true);
    }

    @ApiOperation("给用户分配角色")
    @PostMapping("/allot-user-role")
    public CommonResult<Boolean> assignUserRole(@Validated @RequestBody PermissionAllotUserRoleReqVO reqVO) {
        permissionService.allotUserRole(reqVO.getUserId(), reqVO.getRoleIds());
        return success(true);
    }

}
