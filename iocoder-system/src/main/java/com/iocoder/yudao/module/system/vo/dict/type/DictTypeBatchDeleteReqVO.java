package com.iocoder.yudao.module.system.vo.dict.type;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel("管理后台 - 用户批量删除 Request VO")
@Data
public class DictTypeBatchDeleteReqVO{

    @ApiModelProperty(value = "用户编号集合")
    List<Long> dictTypeIds;
}
