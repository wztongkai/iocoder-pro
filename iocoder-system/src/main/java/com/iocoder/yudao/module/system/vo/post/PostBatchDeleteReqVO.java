package com.iocoder.yudao.module.system.vo.post;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel("管理后台 - 岗位批量删除 Request VO")
@Data
public class PostBatchDeleteReqVO {

    @ApiModelProperty(value = "岗位编号集合")
    List<Long> postIds;
}
