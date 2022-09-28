package com.iocoder.yudao.module.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.iocoder.yudao.module.commons.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 *  代办
 * </p>
 *
 * @author wu kai
 * @since 2022-09-28
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("system_bus_todo")
@ApiModel(value = "BusTodoDO对象")
public class BusTodoDO extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("业务 ID")
    @TableField("bus_id")
    private String busId;

    @ApiModelProperty("流程实例 ID")
    @TableField("instance_id")
    private String instanceId;

    @ApiModelProperty("任务 ID")
    @TableField("task_id")
    private String taskId;

    @ApiModelProperty("标题")
    @TableField("name")
    private String name;

    @ApiModelProperty("内容")
    @TableField("content")
    private String content;

    @ApiModelProperty("业务类型 （关联字典表）")
    @TableField("business_type_code")
    private String businessTypeCode;

    @ApiModelProperty("流程状态编码 （关联字典表）")
    @TableField("process_node_code")
    private String processNodeCode;

    @ApiModelProperty("代办人 ID")
    @TableField("todo_user_id")
    private String todoUserId;

    @ApiModelProperty("代办人姓名")
    @TableField("todo_user_name")
    private String todoUserName;

    @ApiModelProperty("通知代办的时间")
    @TableField("notify_time")
    private LocalDateTime notifyTime;

    @ApiModelProperty("处理人 ID")
    @TableField("handle_user_id")
    private String handleUserId;

    @ApiModelProperty("处理人姓名")
    @TableField("handle_user_name")
    private String handleUserName;

    @ApiModelProperty("处理时间")
    @TableField("handle_time")
    private LocalDateTime handleTime;

    @ApiModelProperty("是否已查看 （0：否 1：是）")
    @TableField("is_view")
    private Integer isView;

    @ApiModelProperty("是否已处理")
    @TableField("is_handle")
    private Integer isHandle;

    @ApiModelProperty("备注")
    @TableField("remark")
    private String remark;


}
