package com.iocoder.yudao.module.activiti.api.definition;

import com.iocoder.yudao.module.activiti.dto.instance.ProcessCurrentTaskInfoDTO;
import com.iocoder.yudao.module.activiti.dto.instance.ProcessOperateDTO;
import com.iocoder.yudao.module.activiti.service.ProcessService;
import com.iocoder.yudao.module.commons.core.domain.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

import static com.iocoder.yudao.module.commons.core.domain.CommonResult.success;

/**
 * @author wu kai
 * @since 2022/10/8
 */
@Api(tags = "管理后台 - 流程操作")
@RestController
@RequestMapping("/bpm/process")
@Validated
public class BpmProcessController {

    @Resource
    ProcessService processService;

    @GetMapping("/get-instance-status")
    @ApiOperation(value = "获得流程实例状态", notes = "用于获取当前流程实例的状态--->挂起：true 激活：false")
    @ApiImplicitParam(name = "instanceId", value = "流程实例编号", required = true, example = "Process_1657609678148:1:9e9c1167-01c0-11ed-92b4-d89ef33ad32f", dataTypeClass = String.class)
    @PreAuthorize("@ss.hasPermission('bpm:process:query')")
    public CommonResult<Boolean> getProcessInstanceStatus(@RequestParam("instanceId") String instanceId) {
        return success(processService.isSuspendInstanceStatus(instanceId));
    }

    @GetMapping("/get-instance-current-taskInfo")
    @ApiOperation(value = "获取流程实例当前任务节点信息", notes = "用于获取流程当前节点的信息")
    @ApiImplicitParam(name = "instanceId", value = "流程实例编号", required = true, example = "Process_1657609678148:1:9e9c1167-01c0-11ed-92b4-d89ef33ad32f", dataTypeClass = String.class)
    @PreAuthorize("@ss.hasPermission('bpm:process:query')")
    public CommonResult<List<ProcessCurrentTaskInfoDTO>> getProcessCurrentTaskInfo(@RequestParam("instanceId") String instanceId) {
        return success(processService.getProcessCurrentTaskInfo(instanceId));
    }

    @PostMapping("/instance-operate")
    @ApiOperation(value = "流程实例操作", notes = "流程实例的挂起，激活，取消")
    @PreAuthorize("@ss.hasPermission('bpm:process:query')")
    public CommonResult<Boolean> instanceOperate(@Valid ProcessOperateDTO operateDTO) {
        return success(processService.instanceOperate(operateDTO));
    }


}
