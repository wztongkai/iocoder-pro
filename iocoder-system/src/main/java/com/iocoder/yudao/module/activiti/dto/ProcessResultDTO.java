package com.iocoder.yudao.module.activiti.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * 流程返回结果
 *
 * @author wu kai
 * @since 2022/7/13
 */

@Data
@Builder
public class ProcessResultDTO {

    @ApiModelProperty(value = "流程定义 KEY ")
    private String definitionKey;

    @ApiModelProperty(value = "流程实例 id ")
    private String instanceId;

    @ApiModelProperty(value = "任务名称")
    private String taskName;

    @ApiModelProperty(value = "任务 KEY ")
    private String taskKey;
}
