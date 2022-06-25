package com.iocoder.yudao.module.system.vo.user;

import com.iocoder.yudao.module.commons.annotation.Mobile;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * 用户 Base VO，提供给添加、修改、详细的子 VO 使用
 */
@Data
public class UserBaseVO {

    @ApiModelProperty(value = "用户账号", required = true, example = "wukai")
    @NotBlank(message = "用户账号不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9]{4,30}$", message = "用户账号由 数字、字母 组成")
    @Size(min = 4, max = 30, message = "用户账号长度为 4-30 个字符")
    private String username;

    @ApiModelProperty(value = "用户昵称", required = true, example = "吴凯")
    @Size(max = 30, message = "用户昵称长度不能超过30个字符")
    private String nickname;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "用户邮箱", example = "2580211264@qq.com")
    @Email(message = "邮箱格式不正确")
    @Size(max = 50, message = "邮箱长度不能超过 50 个字符")
    private String email;

    @ApiModelProperty(value = "手机号码", example = "15601691300")
    @Mobile
    private String mobile;

    @ApiModelProperty(value = "用户性别", example = "1", notes = "参见 SexEnum 枚举类")
    private Integer sex;

    @ApiModelProperty(value = "用户头像", example = "https://www.iocoder.cn/xxx.png")
    private String avatar;

}
