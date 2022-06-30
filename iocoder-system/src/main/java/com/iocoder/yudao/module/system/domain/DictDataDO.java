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
 * 字典数据表
 * </p>
 *
 * @author wu kai
 * @since 2022-06-30
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("system_dict_data")
@ApiModel(value = "DictDataDO对象", description = "字典数据表")
public class DictDataDO extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("字典排序")
    @TableField("sort")
    private Integer sort;

    @ApiModelProperty("字典标签")
    @TableField("label")
    private String label;

    @ApiModelProperty("字典键值")
    @TableField("value")
    private String value;

    @ApiModelProperty("字典类型")
    @TableField("dict_type")
    private String dictType;

    @ApiModelProperty("状态（0正常 1停用）")
    @TableField("status")
    private Integer status;

    @ApiModelProperty("颜色类型")
    @TableField("color_type")
    private String colorType;

    @ApiModelProperty("css 样式")
    @TableField("css_class")
    private String cssClass;

    @ApiModelProperty("备注")
    @TableField("remark")
    private String remark;


}
