package com.iocoder.yudao.module.system.vo.city;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wu kai
 * @since 2022/10/14
 */

@Data
public class CitySimpleRespVO {

    @ApiModelProperty(value = "城市编号")
    private Long id;

    @ApiModelProperty(value = "城市名称")
    private String cityName;
}
