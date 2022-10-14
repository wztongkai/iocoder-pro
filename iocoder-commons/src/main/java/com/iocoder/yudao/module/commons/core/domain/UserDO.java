package com.iocoder.yudao.module.commons.core.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 用户信息表
 * </p>
 * 使用MP  JsonLongSetTypeHandler转换器时，tablename中必须加上autoResultMap = true  否则查询时该字段为空
 *
 * @author wu kai
 * @since 2022-06-22
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName(value = "system_user", autoResultMap = true)
@ApiModel(value = "UserDO对象", description = "用户信息表")
public class UserDO extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户账号")
    @TableField("username")
    private String username;

    @ApiModelProperty("密码")
    @TableField("password")
    private String password;

    @ApiModelProperty("用户昵称")
    @TableField("nickname")
    private String nickname;

    @ApiModelProperty("英文姓")
    @TableField("last_name")
    private String lastName;

    @ApiModelProperty("英文名")
    @TableField("first_name")
    private String firstName;

    @ApiModelProperty("姓名电码")
    @TableField("name_code")
    private String nameCode;

    @ApiModelProperty("出生地")
    @TableField("birth_province")
    private String birthProvince;

    @ApiModelProperty("出生日期")
    @TableField("birthday")
    private String birthday;

    @ApiModelProperty("用户邮箱")
    @TableField("email")
    private String email;

    @ApiModelProperty("手机号码")
    @TableField("mobile")
    private String mobile;

    @ApiModelProperty("用户性别（1、男 2、女）")
    @TableField("sex")
    private Integer sex;

    @ApiModelProperty("头像地址")
    @TableField("avatar")
    private String avatar;

    @ApiModelProperty("备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty("帐号状态（0正常 1停用）")
    @TableField("status")
    private Integer status;

    @ApiModelProperty("最后登录IP")
    @TableField("login_ip")
    private String loginIp;

    @ApiModelProperty("最后登录时间")
    @TableField("login_date")
    private LocalDateTime loginDate;


}
