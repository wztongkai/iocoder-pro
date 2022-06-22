package com.iocoder.yudao.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.iocoder.yudao.commons.core.domain.BaseEntity;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 角色信息表
 * </p>
 *
 * @author wu kai
 * @since 2022-06-22
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("system_role")
@ApiModel(value = "RoleDO对象", description = "角色信息表")
public class RoleDO extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("角色名称")
    @TableField("name")
    private String name;

    @ApiModelProperty("角色权限字符串")
    @TableField("code")
    private String code;

    @ApiModelProperty("显示顺序")
    @TableField("sort")
    private Integer sort;

    @ApiModelProperty("数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）")
    @TableField("data_scope")
    private Integer dataScope;

    @ApiModelProperty("数据范围(指定部门数组)")
    @TableField("data_scope_dept_ids")
    private String dataScopeDeptIds;

    @ApiModelProperty("角色状态（0正常 1停用）")
    @TableField("status")
    private Integer status;

    @ApiModelProperty("角色类型")
    @TableField("type")
    private Integer type;

    @ApiModelProperty("备注")
    @TableField("remark")
    private String remark;


}
