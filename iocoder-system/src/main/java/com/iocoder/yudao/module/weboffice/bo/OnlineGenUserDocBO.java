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

    @ApiModelProperty(value = "昵称")
    private String nc;

    @ApiModelProperty(value = "性别")
    private String xb;

    @ApiModelProperty(value = "邮箱")
    private String yx;

    @ApiModelProperty(value = "电话")
    private String dh;


}
