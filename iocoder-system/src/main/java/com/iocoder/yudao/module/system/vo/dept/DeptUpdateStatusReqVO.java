package com.iocoder.yudao.module.system.vo.dept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@ApiModel("管理后台 - 部门更新状态 Request VO")
@Data
public class DeptUpdateStatusReqVO {

    @ApiModelProperty(value = "部门编号", required = true, example = "1024")
    @NotNull(message = "部门编号不能为空")
    private Long id;

    @ApiModelProperty(value = "状态", required = true, example = "1", notes = "CommonStatusEnum 枚举")
    @NotNull(message = "状态不能为空")
    private Integer status;

}
