package com.iocoder.yudao.module.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.iocoder.yudao.module.commons.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 岗位信息表
 * </p>
 *
 * @author wu kai
 * @since 2022-06-22
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("system_post")
@ApiModel(value = "PostDO对象", description = "岗位信息表")
public class PostDO extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("岗位编码")
    @TableField("code")
    private String code;

    @ApiModelProperty("岗位名称")
    @TableField("name")
    private String name;

    @ApiModelProperty("显示顺序")
    @TableField("sort")
    private Integer sort;

    @ApiModelProperty("状态（0正常 1停用）")
    @TableField("status")
    private Integer status;

    @ApiModelProperty("备注")
    @TableField("remark")
    private String remark;


}
