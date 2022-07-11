package com.iocoder.yudao.module.activiti.vo.definition;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 流程定义创建 Vo
 *
 * @author wu kai
 * @since 2022/7/11
 */

@Data
public class BpmProcessDefinitionCreateReqVO {

    @NotEmpty(message = "流程模型编号不能为空")
    private String id;

    @NotEmpty(message = "流程标识不能为空")
    private String key;

    @NotEmpty(message = "流程名称不能为空")
    private String name;

    private String description;

    @NotEmpty(message = "BPMN XML 不能为空")
    private byte[] bpmnBytes;
}
