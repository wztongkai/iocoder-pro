package com.iocoder.yudao.module.system.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.util.List;

@ApiModel("管理后台 - 用户更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class UserUpdateReqVO extends UserBaseVO {

    @ApiModelProperty(value = "用户编号", required = true, example = "1024")
    @NotNull(message = "用户编号不能为空")
    private Long id;

    @ApiModelProperty(value = "部门编号")
    private List<Long> deptIds;

    @ApiModelProperty(value = "岗位编号")
    private List<Long> postIds;

}
