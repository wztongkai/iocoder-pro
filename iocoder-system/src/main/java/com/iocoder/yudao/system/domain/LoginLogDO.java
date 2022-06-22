package com.iocoder.yudao.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.iocoder.yudao.commons.core.domain.BaseEntity;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统访问记录
 * </p>
 *
 * @author wu kai
 * @since 2022-06-22
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("system_login_log")
@ApiModel(value = "LoginLogDO对象", description = "系统访问记录")
public class LoginLogDO extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("日志类型")
    @TableField("log_type")
    private Long logType;

    @ApiModelProperty("链路追踪编号")
    @TableField("trace_id")
    private String traceId;

    @ApiModelProperty("用户编号")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty("用户类型")
    @TableField("user_type")
    private Integer userType;

    @ApiModelProperty("用户账号")
    @TableField("username")
    private String username;

    @ApiModelProperty("登陆结果")
    @TableField("result")
    private Integer result;

    @ApiModelProperty("用户 IP")
    @TableField("user_ip")
    private String userIp;

    @ApiModelProperty("浏览器 UA")
    @TableField("user_agent")
    private String userAgent;


}
