package com.iocoder.yudao.module.weboffice.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wu kai
 * @since 2022/10/13
 */
@Data
public class OnlineGenUserDocBO {

    @ApiModelProperty(value = "序号")
    private Integer xh;

    @ApiModelProperty(value = "姓名")
    private String xm;

    @ApiModelProperty(value = "性别")
    private String xb;

    @ApiModelProperty(value = "出生日期")
    private String csrq;

    @ApiModelProperty(value = "出生地")
    private String csd;

    @ApiModelProperty(value = "电话")
    private String dh;

    @ApiModelProperty(value = "邮箱")
    private String yx;




}
