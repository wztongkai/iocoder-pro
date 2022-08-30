package com.iocoder.yudao.module.system.vo.permission.menu;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("管理后台 - 菜单列表 Request VO")
@Data
public class MenuListReqVO {

    @ApiModelProperty(value = "菜单名称", example = "用户", notes = "模糊匹配")
    private String name;

    @ApiModelProperty(value = "展示状态", example = "1")
    private Integer status;

}
