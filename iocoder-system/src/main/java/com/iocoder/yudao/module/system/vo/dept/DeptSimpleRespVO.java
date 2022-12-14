package com.iocoder.yudao.module.system.vo.dept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("管理后台 - 部门精简信息 Response VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeptSimpleRespVO {

    @ApiModelProperty(value = "部门编号", required = true, example = "100")
    private Long id;

    @ApiModelProperty(value = "部门名称", required = true, example = "开发部")
    private String name;

    @ApiModelProperty(value = "父部门 ID", required = true, example = "1")
    private Long parentId;

}
