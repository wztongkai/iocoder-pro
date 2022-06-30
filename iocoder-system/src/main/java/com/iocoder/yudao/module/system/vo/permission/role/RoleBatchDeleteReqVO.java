package com.iocoder.yudao.module.system.vo.permission.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel("管理后台 - 角色批量删除 Request VO")
@Data
public class RoleBatchDeleteReqVO {

    @ApiModelProperty(value = "角色编号集合")
    List<Long> roleIds;
}
