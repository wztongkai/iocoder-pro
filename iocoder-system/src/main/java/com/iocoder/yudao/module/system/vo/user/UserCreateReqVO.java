package com.iocoder.yudao.module.system.vo.user;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@ApiModel("管理后台 - 用户创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class UserCreateReqVO extends UserBaseVO {

//    @ApiModelProperty(value = "密码", required = true, example = "123456")
//    @NotEmpty(message = "密码不能为空")
//    @Length(min = 4, max = 16, message = "密码长度为 4-16 位")
//    private String password;

    @ApiModelProperty(value = "部门编号")
    private List<Long> deptIds;

    @ApiModelProperty(value = "岗位编号")
    private List<Long> postIds;

}
