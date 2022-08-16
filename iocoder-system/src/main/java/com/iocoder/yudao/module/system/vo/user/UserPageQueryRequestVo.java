package com.iocoder.yudao.module.system.vo.user;

import com.iocoder.yudao.module.commons.core.domain.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author: kai wu
 * @Date: 2022/6/12 18:22
 */
@ApiModel("管理后台 - 用户分页 Request VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserPageQueryRequestVo extends PageParam {

    @ApiModelProperty(value = "搜索值", notes = "模糊匹配")
    private String search;

    @ApiModelProperty(value = "创建时间", example = "[2022-07-01 00:00:00,2022-07-01 23:59:59]")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date[] createTime;

//    @ApiModelProperty(value = "用户账号", notes = "模糊匹配")
//    private String username;
//
//    @ApiModelProperty(value = "手机号码", notes = "模糊匹配")
//    private String mobile;

    @ApiModelProperty(value = "展示状态", example = "1", notes = "UserStatus 枚举类")
    private Integer status;

//    @ApiModelProperty(value = "开始时间", example = "2022-06-11")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private Date beginTime;
//
//    @ApiModelProperty(value = "结束时间", example = "2022-06-11")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    private Date endTime;

    @ApiModelProperty(value = "部门编号", example = "1024", notes = "同时筛选子部门")
    private Long deptId;
}
