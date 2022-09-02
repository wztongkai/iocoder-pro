package com.iocoder.yudao.module.system.vo.logs.operatelog;

import com.iocoder.yudao.module.commons.core.domain.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@ApiModel("管理后台 - 操作日志分页列表 Request VO")
@Data
public class OperateLogPageReqVO extends PageParam {

    @ApiModelProperty(value = "操作模块", example = "订单", notes = "模拟匹配")
    private String module;

    @ApiModelProperty(value = "用户昵称", example = "管理员", notes = "模拟匹配")
    private String userNickname;

    @ApiModelProperty(value = "操作分类", example = "1", notes = " OperateLogTypeEnum 枚举类")
    private String type;

    @ApiModelProperty(value = "操作状态", example = "true")
    private Boolean status;

    @ApiModelProperty(value = "创建时间", example = "[2022-07-01 00:00:00,2022-07-01 23:59:59]")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date[] createTime;


}
