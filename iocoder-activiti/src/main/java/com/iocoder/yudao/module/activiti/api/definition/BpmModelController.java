package com.iocoder.yudao.module.activiti.api.definition;

import com.iocoder.yudao.module.activiti.service.BpmModelService;
import com.iocoder.yudao.module.activiti.vo.model.BpmModeImportReqVO;
import com.iocoder.yudao.module.activiti.vo.model.BpmModelCreateReqVO;
import com.iocoder.yudao.module.activiti.vo.model.BpmModelRespVO;
import com.iocoder.yudao.module.commons.annotation.Log;
import com.iocoder.yudao.module.commons.core.domain.CommonResult;
import com.iocoder.yudao.module.commons.enums.BusinessType;
import com.iocoder.yudao.module.commons.utils.BeanUtil;
import com.iocoder.yudao.module.commons.utils.io.IoUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.IOException;

import static com.iocoder.yudao.module.commons.core.domain.CommonResult.success;

/**
 * @author wu kai
 * @since 2022/7/11
 */
@Api(tags = "管理后台 - 流程模型")
@RestController
@RequestMapping("/bpm/model")
@Validated
public class BpmModelController {

    @Resource
    BpmModelService bpmModelService;


    @Log(title = "流程模型", businessType = BusinessType.INSERT)
    @PreAuthorize("@ss.hasPermission('bpm:model:create')")
    @PostMapping("/createModel")
    @ApiOperation(value = "新建模型")
    public CommonResult<String> createModel(@Valid @RequestBody BpmModelCreateReqVO createRetVO) {
        return success(bpmModelService.createModel(createRetVO, null));
    }

    @PostMapping("/importModel")
    @ApiOperation(value = "导入模型")
    @PreAuthorize("@ss.hasPermission('bpm:model:import')")
    public CommonResult<String> importModel(@Valid BpmModeImportReqVO importReqVO) throws IOException {
        BpmModelCreateReqVO bpmModelCreateReqVO = new BpmModelCreateReqVO();
        BeanUtil.copyProperties(importReqVO, bpmModelCreateReqVO);
        // 读取文件
        String bpmnXml = IoUtils.readUtf8(importReqVO.getBpmnFile().getInputStream(), false);
        return success(bpmModelService.createModel(bpmModelCreateReqVO, bpmnXml));
    }

    @GetMapping("/getModel")
    @ApiOperation("获得流程模型")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "990fc7a3-01c0-11ed-92b4-d89ef33ad32f", dataTypeClass = String.class)
    @PreAuthorize("@ss.hasPermission('bpm:model:query')")
    public CommonResult<BpmModelRespVO> getModel(@RequestParam("id") String id) {
        BpmModelRespVO model = bpmModelService.getModel(id);
        return success(model);
    }

    @PostMapping("/deployModel")
    @ApiOperation("部署流程模型")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "990fc7a3-01c0-11ed-92b4-d89ef33ad32f", dataTypeClass = String.class)
    public CommonResult<Boolean> deployModel(@RequestParam("id") String id) {
        bpmModelService.deployModel(id);
        return success(true);
    }


}
