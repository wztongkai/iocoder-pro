package com.iocoder.yudao.module.system.api.dept;


import com.iocoder.yudao.module.commons.annotation.Log;
import com.iocoder.yudao.module.commons.core.domain.CommonResult;
import com.iocoder.yudao.module.commons.enums.BusinessType;
import com.iocoder.yudao.module.commons.enums.common.CommonStatusEnum;
import com.iocoder.yudao.module.commons.utils.BeanUtil;
import com.iocoder.yudao.module.system.service.DeptService;
import com.iocoder.yudao.module.system.vo.dept.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static com.iocoder.yudao.module.commons.core.domain.CommonResult.success;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author wu kai
 * @since 2022-06-22
 */
@Api(tags = "管理后台 - 部门")
@RestController
@RequestMapping("/system/dept")
@Validated
public class DeptController {

    @Resource
    DeptService deptService;

    @Log(title = "部门管理",businessType = BusinessType.INSERT)
    @PreAuthorize("@ss.hasPermission('system:dept:create')")
    @PostMapping("create")
    @ApiOperation("创建部门")
    public CommonResult<Long> createDept(@Valid @RequestBody DeptCreateReqVO reqVO) {
        return success(deptService.createDept(reqVO));
    }

    @Log(title = "部门管理",businessType = BusinessType.UPDATE)
    @PreAuthorize("@ss.hasPermission('system:dept:update')")
    @PutMapping("update")
    @ApiOperation("更新部门")
    public CommonResult<Boolean> updateDept(@Valid @RequestBody DeptUpdateReqVO reqVO) {
        deptService.updateDept(reqVO);
        return success(true);
    }

    @Log(title = "部门管理",businessType = BusinessType.UPDATE)
    @PreAuthorize("@ss.hasPermission('system:dept:update-status')")
    @PutMapping("/update-status")
    @ApiOperation("修改部门状态")
    public CommonResult<Boolean> updateDeptStatus(@Valid @RequestBody DeptUpdateStatusReqVO updateStatusReqVO) {
        deptService.updateDeptStatus(updateStatusReqVO);
        return success(true);
    }

    @Log(title = "部门管理",businessType = BusinessType.DELETE)
    @PreAuthorize("@ss.hasPermission('system:dept:delete')")
    @DeleteMapping("delete")
    @ApiOperation("删除部门")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    public CommonResult<Boolean> deleteDept(@RequestParam("id") Long id) {
        deptService.deleteDept(id);
        return success(true);
    }

    @Log(title = "部门管理",businessType = BusinessType.SELECT)
    @PreAuthorize("@ss.hasPermission('system:dept:list')")
    @GetMapping("/list")
    @ApiOperation("获取部门列表")
    public CommonResult<List<DeptRespVO>> listDepts(DeptListReqVO reqVO) {
        List<DeptRespVO> list = deptService.getSimpleDepts(reqVO);
        return success(list);
    }

    @Log(title = "部门管理",businessType = BusinessType.SELECT)
    @GetMapping("/list-all-simple")
    @ApiOperation(value = "获取部门精简信息列表", notes = "只包含被开启的部门，主要用于前端的下拉选项")
    public CommonResult<List<DeptSimpleRespVO>> getSimpleDepts() {
        // 获得部门列表，只要开启状态的
        DeptListReqVO reqVO = new DeptListReqVO();
        reqVO.setStatus(CommonStatusEnum.ENABLE.getStatus());
        List<DeptRespVO> list = deptService.getSimpleDepts(reqVO);
        List<DeptSimpleRespVO> simpleRespList = new ArrayList<>();
        BeanUtil.copyListProperties(list, simpleRespList, DeptSimpleRespVO.class);
        return success(simpleRespList);
    }

    @Log(title = "部门管理",businessType = BusinessType.SELECT)
    @GetMapping("/get-info")
    @ApiOperation("获得部门信息")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    public CommonResult<DeptRespVO> getDeptInfo(@RequestParam("id") Long id) {
        return success(deptService.getDeptInfo(id));
    }

}
