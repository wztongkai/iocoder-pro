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
    private Integer type;

    @ApiModelProperty(value = "操作状态", example = "true")
    private Boolean success;

    @ApiModelProperty(value = "开始时间", example = "2020-10-24")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date beginTime;

    @ApiModelProperty(value = "结束时间", example = "2020-10-24")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

}
