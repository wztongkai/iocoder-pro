package com.iocoder.yudao.module.system.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wu kai
 * @since 2022/10/14
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "姓名分割为姓氏+名称 Response VO")
public class UserNameSplitRespVO {

    @ApiModelProperty(value ="姓")
    private String lastName;

    @ApiModelProperty(value = "名")
    private String firstName;
}
