package com.iocoder.yudao.module.activiti.api.definition;

import com.iocoder.yudao.module.activiti.service.BpmProcessDefinitionService;
import com.iocoder.yudao.module.activiti.vo.definition.BpmProcessDefinitionListReqVO;
import com.iocoder.yudao.module.activiti.vo.definition.BpmProcessDefinitionRespVO;
import com.iocoder.yudao.module.commons.core.domain.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

import static com.iocoder.yudao.module.commons.core.domain.CommonResult.success;

/**
 * @author wu kai
 * @since 2022/7/12
 */

@Api(tags = "管理后台 - 流程定义")
@RestController
@RequestMapping("/bpm/process-definition")
@Validated
public class BpmProcessDefinitionController {

    @Resource
    BpmProcessDefinitionService processDefinitionService;

    @GetMapping("/list")
    @ApiOperation(value = "获得流程定义列表")
    @PreAuthorize("@ss.hasPermission('bpm:process-definition:query')")
    public CommonResult<List<BpmProcessDefinitionRespVO>> getProcessDefinitionList(
            BpmProcessDefinitionListReqVO listReqVO) {
        return success(processDefinitionService.getProcessDefinitionList(listReqVO));
    }
}
