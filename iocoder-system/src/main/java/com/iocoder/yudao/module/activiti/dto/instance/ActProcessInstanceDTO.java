package com.iocoder.yudao.module.activiti.dto.instance;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "流程实例 DTO (包含正在运行中的流程实例及历史流程实例)")
public class ActProcessInstanceDTO {

    @ApiModelProperty(value = "流程实例id")
    private String instanceId;

    @ApiModelProperty(value = "流程定义id")
    private String processDefinitionId;

    @ApiModelProperty(value = "流程定义key")
    private String processDefinitionKey;
}
