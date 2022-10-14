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
 * 
 * </p>
 *
 * @author wu kai
 * @since 2022-10-14
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("system_name_code")
@ApiModel(value = "NameCodeDO对象", description = "")
public class NameCodeDO extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("姓名")
    @TableField("name")
    private String name;

    @ApiModelProperty("电码")
    @TableField("code")
    private String code;

    @ApiModelProperty("读音")
    @TableField("pronounce")
    private String pronounce;


}
