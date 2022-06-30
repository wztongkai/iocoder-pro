package com.iocoder.yudao.module.system.vo.logs.loginlog;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel("管理后台 - 登录日志批量删除 Request VO")
@Data
public class LoginLogBatchDeleteReqVO {

    @ApiModelProperty(value = "登录日志编号集合")
    List<Long> logIds;
}
