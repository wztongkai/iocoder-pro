package com.iocoder.yudao.module.system.vo.post;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@ApiModel("管理后台 - 岗位列表 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class PostListReqVO extends PostBaseVO {

    @ApiModelProperty(value = "岗位名称", example = "芋道", notes = "模糊匹配")
    private String name;

    @ApiModelProperty(value = "搜索", example = "项目经理/ceo", notes = "岗位编号或岗位名称模糊匹配")
    private String search;

    @ApiModelProperty(value = "展示状态", example = "1", notes = "CommonStatusEnum 枚举类")
    private Integer status;

    @ApiModelProperty(value = "创建时间", example = "[2022-07-01 00:00:00,2022-07-01 23:59:59]")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date[] createTime;

}
