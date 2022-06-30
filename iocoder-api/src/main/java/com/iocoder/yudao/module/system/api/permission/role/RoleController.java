package com.iocoder.yudao.module.system.api.permission.role;

import com.iocoder.yudao.module.commons.annotation.Log;
import com.iocoder.yudao.module.commons.core.domain.CommonResult;
import com.iocoder.yudao.module.commons.core.domain.PageResult;
import com.iocoder.yudao.module.commons.enums.BusinessType;
import com.iocoder.yudao.module.commons.enums.common.CommonStatusEnum;
import com.iocoder.yudao.module.commons.utils.BeanUtil;
import com.iocoder.yudao.module.system.domain.RoleDO;
import com.iocoder.yudao.module.system.service.RoleService;
import com.iocoder.yudao.module.system.vo.permission.role.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.iocoder.yudao.module.commons.core.domain.CommonResult.success;

/**
 * <p>
 * 角色信息表 前端控制器
 * </p>
 *
 * @author wu kai
 * @since 2022-06-27
 */

@Api(tags = "管理后台 - 角色管理")
@RestController
@RequestMapping("/system/role")
@Validated
public class RoleController {

    @Resource
    RoleService roleService;

    @Log(title = "角色管理", businessType = BusinessType.INSERT)
    @PostMapping("/create")
    @ApiOperation("创建角色")
    public CommonResult<Long> createRole(@Valid @RequestBody RoleCreateReqVO createReqVO) {
        return success(roleService.createRole(createReqVO));
    }

    @Log(title = "角色管理", businessType = BusinessType.UPDATE)
    @PutMapping("/update")
    @ApiOperation("修改角色")
    public CommonResult<Boolean> updateRole(@Valid @RequestBody RoleUpdateReqVO updateReqVO) {
        roleService.updateRole(updateReqVO);
        return success(true);
    }

    @Log(title = "角色管理", businessType = BusinessType.UPDATE)
    @PutMapping("/update-status")
    @ApiOperation("修改角色状态")
    public CommonResult<Boolean> updateRoleStatus(@Valid @RequestBody RoleUpdateStatusReqVO updateStatusReqVO) {
        roleService.updateRoleStatus(updateStatusReqVO);
        return success(true);
    }

    @Log(title = "角色管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/delete")
    @ApiOperation("删除角色")
    @ApiImplicitParam(name = "id", value = "角色编号", required = true, example = "1024", dataTypeClass = Long.class)
    public CommonResult<Boolean> deleteRole(@RequestParam("id") Long id) {
        roleService.deleteRole(id);
        return success(true);
    }

    @Log(title = "角色管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/delete-role-batch")
    @ApiOperation("删除角色")
    public CommonResult<Boolean> deleteRoleBatch(@Valid @RequestBody RoleBatchDeleteReqVO batchDeleteReqVO){
        roleService.deleteRoleBatch(batchDeleteReqVO);
        return success(true);
    }

    @GetMapping("/get-role-info")
    @ApiOperation("获取角色详情")
    @ApiImplicitParam(name = "id", value = "角色编号", required = true, example = "1024", dataTypeClass = Long.class)
    public CommonResult<RoleRespVO> getRoleInfo(@RequestParam("id") Long id) {
        return success(roleService.getRoleInfo(id));
    }

    @GetMapping("/get-role-page")
    @ApiOperation("获得角色分页")
    public CommonResult<PageResult<RoleDO>> getRolePage(RolePageReqVO reqVO) {
        return success(roleService.getRolePage(reqVO));
    }

    @GetMapping("/list-all-simple")
    @ApiOperation(value = "获取角色精简信息列表", notes = "只包含被开启的角色，主要用于前端的下拉选项")
    public CommonResult<List<RoleSimpleRespVO>> getSimpleRoles() {
        // 获得角色列表，只要开启状态的
        List<RoleDO> list = roleService.getRoles(Collections.singleton(CommonStatusEnum.ENABLE.getStatus()));
        List<RoleSimpleRespVO> simpleRespList = new ArrayList<>();
        BeanUtil.copyListProperties(list, simpleRespList, RoleSimpleRespVO.class);
        return success(simpleRespList);
    }

}
