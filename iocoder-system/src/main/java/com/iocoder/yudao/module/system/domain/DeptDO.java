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
 *
 * </p>
 *
 * @author wu kai
 * @since 2022-06-22
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("system_dept")
@ApiModel(value = "DeptDO对象", description = "")
public class DeptDO extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("部门名称")
    @TableField("name")
    private String name;

    @ApiModelProperty("父部门id")
    @TableField("parent_id")
    private Long parentId;

    @ApiModelProperty("显示顺序")
    @TableField("sort")
    private Integer sort;

    @ApiModelProperty("负责人")
    @TableField("leader_user_id")
    private Long leaderUserId;

    @ApiModelProperty("联系电话")
    @TableField("phone")
    private String phone;

    @ApiModelProperty("邮箱")
    @TableField("email")
    private String email;

    @ApiModelProperty("部门状态（0正常 1停用）")
    @TableField("status")
    private Integer status;


}
