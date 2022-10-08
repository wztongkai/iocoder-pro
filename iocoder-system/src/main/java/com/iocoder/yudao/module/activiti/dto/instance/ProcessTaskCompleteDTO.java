package com.iocoder.yudao.module.activiti.dto.instance;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;


@Data
@ApiModel(value = "流程任务 DTO")
public class ProcessTaskCompleteDTO {

    @ApiModelProperty(value = "任务id")
    private String taskId;

    @ApiModelProperty(value = "处理人id")
    private String handleUserId;

    @ApiModelProperty(value = "处理人姓名")
    private String handleUserName;

    @ApiModelProperty(value = "流程变量（业务层中，不同流程，定义不同的variablesBO）")
    Map<String, Object> variables;

    @ApiModelProperty(value = "处理结果")
    private String handleResult;

    @ApiModelProperty(value = "业务id")
    private String busId;

    @ApiModelProperty("备注")
    private String remark;
}
