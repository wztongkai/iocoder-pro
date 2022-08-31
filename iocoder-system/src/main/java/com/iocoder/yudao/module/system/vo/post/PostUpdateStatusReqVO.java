package com.iocoder.yudao.module.system.vo.post;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@ApiModel("管理后台 - 岗位更新状态 Request VO")
@Data
public class PostUpdateStatusReqVO {

    @ApiModelProperty(value = "岗位编号", required = true, example = "1024")
    @NotNull(message = "岗位编号不能为空")
    private Long id;

    @ApiModelProperty(value = "状态", required = true, example = "1", notes = "CommonStatusEnum 枚举")
    @NotNull(message = "状态不能为空")
    private Integer status;

}
