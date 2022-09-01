package com.iocoder.yudao.module.system.vo.dict.type;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@ApiModel("管理后台 - 字典数据更新状态 Request VO")
@Data
public class DictTypeUpdateStatusReqVO {

    @ApiModelProperty(value = "字典类型编号", required = true, example = "1024")
    @NotNull(message = "字典类型编号不能为空")
    private Long id;

    @ApiModelProperty(value = "状态", required = true, example = "1", notes = "CommonStatusEnum 枚举")
    @NotNull(message = "状态不能为空")
    private Integer status;

}
