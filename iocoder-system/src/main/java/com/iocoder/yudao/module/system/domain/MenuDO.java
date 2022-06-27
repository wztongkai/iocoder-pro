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
 * 菜单权限表
 * </p>
 *
 * @author wu kai
 * @since 2022-06-27
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("system_menu")
@ApiModel(value = "MenuDO对象", description = "菜单权限表")
public class MenuDO extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("菜单名称")
    @TableField("name")
    private String name;

    @ApiModelProperty("权限标识")
    @TableField("permission")
    private String permission;

    @ApiModelProperty("菜单类型")
    @TableField("type")
    private Integer type;

    @ApiModelProperty("显示顺序")
    @TableField("sort")
    private Integer sort;

    @ApiModelProperty("父菜单ID")
    @TableField("parent_id")
    private Long parentId;

    @ApiModelProperty("路由地址")
    @TableField("path")
    private String path;

    @ApiModelProperty("菜单图标")
    @TableField("icon")
    private String icon;

    @ApiModelProperty("组件路径")
    @TableField("component")
    private String component;

    @ApiModelProperty("菜单状态")
    @TableField("status")
    private Integer status;

    @ApiModelProperty("是否可见")
    @TableField("visible")
    private Boolean visible;

    @ApiModelProperty("是否缓存")
    @TableField("keep_alive")
    private Boolean keepAlive;


}
