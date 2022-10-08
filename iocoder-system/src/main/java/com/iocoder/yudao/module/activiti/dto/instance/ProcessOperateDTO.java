package com.iocoder.yudao.module.activiti.dto.instance;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wu kai
 * @since 2022/10/8
 */
@Data
@ApiModel(value = "流程实例请求 Request DTO")
public class ProcessOperateDTO {
    @ApiModelProperty(value = "流程实例编号")
    private String instanceId;

    @ApiModelProperty(value = "操作类型",notes = "挂起：suspend 激活：activate 取消：cancel")
    private String optType;


}
