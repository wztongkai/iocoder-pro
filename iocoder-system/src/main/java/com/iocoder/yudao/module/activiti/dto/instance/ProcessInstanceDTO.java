package com.iocoder.yudao.module.activiti.dto.instance;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

/**
 * @author wu kai
 * @since 2022/7/13
 */

@Data
@ApiModel(value = "流程实例请求 Request DTO")
public class ProcessInstanceDTO {

    @ApiModelProperty(value = "流程定义 KEY ")
    private String processDefinitionKey;

    @ApiModelProperty(value = "业务流程 KEY(业务表的id)")
    private String businessKey;

    @ApiModelProperty(value = "流程参数")
    Map<String, Object> variables;

    @ApiModelProperty(value = "流程提交人 Id ")
    private String submitById;

    @ApiModelProperty(value = "备注")
    private String remarks;

}
