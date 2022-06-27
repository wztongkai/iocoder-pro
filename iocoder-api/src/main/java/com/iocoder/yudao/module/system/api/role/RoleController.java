package com.iocoder.yudao.module.system.api.role;

import com.iocoder.yudao.module.commons.core.domain.CommonResult;
import com.iocoder.yudao.module.system.service.RoleService;
import com.iocoder.yudao.module.system.vo.role.RoleCreateReqVO;
import com.iocoder.yudao.module.system.vo.role.RoleUpdateReqVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static com.iocoder.yudao.module.commons.core.domain.CommonResult.success;

/**
 * @author wu kai
 * @date 2022/6/27
 */

@Api(tags = "管理后台 - 角色管理")
@RestController
@RequestMapping("/system/role")
@Validated
public class RoleController {

    @Resource
    private RoleService roleService;

    @PostMapping("/create")
    @ApiOperation("创建角色")
    public CommonResult<Long> createRole(@Valid @RequestBody RoleCreateReqVO createReqVO) {
        return success(roleService.createRole(createReqVO));
    }

    @PutMapping("/update")
    @ApiOperation("修改角色")
    public CommonResult<Boolean> updateRole(@Valid @RequestBody RoleUpdateReqVO updateReqVO){
        roleService.updateRole(updateReqVO);
        return success(true);
    }
}
