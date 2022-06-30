package com.iocoder.yudao.module.system.vo.logs.operatelog;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel("管理后台 - 操作日志批量删除 Request VO")
@Data
public class OperateLogBatchDeleteReqVO {

    @ApiModelProperty(value = "操作日志编号集合")
    List<Long> logIds;
}
