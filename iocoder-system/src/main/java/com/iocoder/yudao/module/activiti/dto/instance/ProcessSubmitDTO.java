package com.iocoder.yudao.module.activiti.dto.instance;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;


@Data
@ApiModel(value = "流程提交 DTO")
public class ProcessSubmitDTO {

    @ApiModelProperty(value = "流程定义 KEY" ,notes = "对应代办表中business_type_code字段")
    private String processDefinitionKey;

    @ApiModelProperty(value = "业务流程 KEY(业务表的id)")
    private String businessKey;

    @ApiModelProperty(value = "流程参数")
    Map<String, Object> variables;

    @ApiModelProperty(value = "流程提交人 Id ")
    private String submitById;

    @ApiModelProperty(value = "标题")
    private String name;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "备注")
    private String remark;

}
