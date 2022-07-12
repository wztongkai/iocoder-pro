package com.iocoder.yudao.module.activiti.vo.definition;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@ApiModel("管理后台 - 流程定义 Response VO")
@Data
@Builder
public class BpmProcessDefinitionRespVO {

    @ApiModelProperty(value = "流程定义ID")
    private String id;

    @ApiModelProperty(value = "流程定义版本")
    private Integer version;

    @ApiModelProperty(value = "流程定义名称")
    @NotEmpty(message = "流程定义名称不能为空")
    private String name;

    @ApiModelProperty(value = "流程描述", example = "我是描述")
    private String description;

    @ApiModelProperty(value = "流程分类", example = "1")
    @NotEmpty(message = "流程分类不能为空")
    private String category;

    @ApiModelProperty(name = "流程定义Key")
    private String key;

    @ApiModelProperty(name = "流程定义资源名称")
    private String resourceName;

    @ApiModelProperty(name = "流程部署ID")
    private String deploymentId;

}
