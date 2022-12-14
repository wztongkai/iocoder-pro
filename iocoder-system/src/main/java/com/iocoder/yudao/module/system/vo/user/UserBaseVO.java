package com.iocoder.yudao.module.system.vo.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.iocoder.yudao.module.commons.annotation.Mobile;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * 用户 Base VO，提供给添加、修改、详细的子 VO 使用
 */
@Data
public class UserBaseVO {

    @ApiModelProperty(value = "用户名称", required = true, example = "wukai")
    @NotBlank(message = "用户名称不能为空")
    private String username;

    @ApiModelProperty(value = "用户昵称", required = true, example = "吴凯")
    @Size(min = 4, max = 30, message = "用户昵称长度为 4-30 个字符")
    private String nickname;

    @ApiModelProperty("英文姓")
    private String lastName;

    @ApiModelProperty("英文名")
    private String firstName;

    @ApiModelProperty("姓名电码")
    private String nameCode;

    @ApiModelProperty("出生地")
    private String birthProvince;

    @ApiModelProperty("出生日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime birthday;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "用户邮箱", example = "2580211264@qq.com")
    @Email(message = "邮箱格式不正确")
    @Size(max = 50, message = "邮箱长度不能超过 50 个字符")
    private String email;

    @ApiModelProperty(value = "手机号码", example = "15601691300")
    @Mobile
    private String mobile;

    @ApiModelProperty(value = "用户性别", example = "1", notes = " SexEnum 枚举类")
    private Integer sex;

    @ApiModelProperty(value = "用户头像", example = "https://www.iocoder.cn/xxx.png")
    private String avatar;

}
