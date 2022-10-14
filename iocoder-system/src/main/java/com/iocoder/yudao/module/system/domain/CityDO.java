package com.iocoder.yudao.module.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.iocoder.yudao.module.commons.core.domain.BaseEntity;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 中国城市表
 * </p>
 *
 * @author wu kai
 * @since 2022-10-14
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("system_city")
@ApiModel(value = "CityDO对象", description = "中国城市表")
public class CityDO extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("城市名称")
    @TableField("city_name")
    private String cityName;

    @ApiModelProperty("父id")
    @TableField("parent_id")
    private Long parentId;


}
