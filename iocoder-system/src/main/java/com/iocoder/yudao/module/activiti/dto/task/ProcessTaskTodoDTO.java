package com.iocoder.yudao.module.activiti.dto.task;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wu kai
 * @since 2022/7/13
 */

@Data
@ApiModel(value = "流程任务代办 数据传输DTO")
public class ProcessTaskTodoDTO {

    @ApiModelProperty(value = "流程实例id")
    private String instanceId;

    @ApiModelProperty(value = "业务id")
    private String businessId;

    @ApiModelProperty(value = "备注")
    private String remarks;
}
