package com.iocoder.yudao.module.system.vo.dept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("管理后台 - 部门列表 Request VO")
@Data
public class DeptListReqVO {

    @ApiModelProperty(value = "部门名称", example = "芋道", notes = "模糊匹配")
    private String name;

    @ApiModelProperty(value = "部门名称或部门负责人姓名", example = "开发部", notes = "模糊匹配")
    private String search;

    @ApiModelProperty(value = "展示状态", example = "1")
    private Integer status;

}
