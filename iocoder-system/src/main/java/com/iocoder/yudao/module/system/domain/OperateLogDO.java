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
 * 操作日志记录
 * </p>
 *
 * @author wu kai
 * @since 2022-06-22
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("system_operate_log")
@ApiModel(value = "OperateLogDO对象", description = "操作日志记录")
public class OperateLogDO extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户编号")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty("用户类型")
    @TableField("user_type")
    private Integer userType;

    @ApiModelProperty("模块标题")
    @TableField("module")
    private String module;

    @ApiModelProperty("操作分类")
    @TableField("type")
    private String type;

    @ApiModelProperty("请求方法名")
    @TableField("request_method")
    private String requestMethod;

    @ApiModelProperty("请求地址")
    @TableField("request_url")
    private String requestUrl;

    @ApiModelProperty("用户 IP")
    @TableField("user_ip")
    private String userIp;

    @ApiModelProperty("操作地点")
    private String operLocation;

    @ApiModelProperty("浏览器 UA")
    @TableField("user_agent")
    private String userAgent;

    @ApiModelProperty("Java 方法名")
    @TableField("java_method")
    private String javaMethod;

    @ApiModelProperty("Java 方法的参数")
    @TableField("java_method_args")
    private String javaMethodArgs;

    @ApiModelProperty("操作时间")
    @TableField("start_time")
    private LocalDateTime startTime;

    @ApiModelProperty("执行时长")
    @TableField("duration")
    private Integer duration;

    @ApiModelProperty("结果码")
    @TableField("result_code")
    private Integer resultCode;

    @ApiModelProperty("结果提示")
    @TableField("result_msg")
    private String resultMsg;

    @ApiModelProperty("结果数据")
    @TableField("result_data")
    private String resultData;

    @ApiModelProperty("错误信息")
    @TableField("error_msg")
    private String errorMsg;


}
