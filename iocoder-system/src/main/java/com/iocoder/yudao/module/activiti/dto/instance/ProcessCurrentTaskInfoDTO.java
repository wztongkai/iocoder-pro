package com.iocoder.yudao.module.activiti.dto.instance;

import com.iocoder.yudao.module.commons.core.domain.UserDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author wu kai
 * @since 2022/10/8
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "流程当前任务信息 Response VO")
public class ProcessCurrentTaskInfoDTO {

    @ApiModelProperty(name = "流程定义Key")
    private String definitionKey;

    @ApiModelProperty(name = "流程实例id")
    private String instanceId;

    @ApiModelProperty(name = "任务ID")
    private String taskId;

    @ApiModelProperty(name = "任务名称")
    private String taskName;

    @ApiModelProperty(name = "任务Key")
    private String taskKey;

    @ApiModelProperty(name = "任务处理人")
    private List<UserDO> handleUserList;

}
