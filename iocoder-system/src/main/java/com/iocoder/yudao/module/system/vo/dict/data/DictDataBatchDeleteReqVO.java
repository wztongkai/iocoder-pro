package com.iocoder.yudao.module.system.vo.dict.data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel("管理后台 - 字典数据批量删除 Request VO")
@Data
public class DictDataBatchDeleteReqVO {

    @ApiModelProperty(value = "字典数据编号集合")
    List<Long> dictDataIds;
}
