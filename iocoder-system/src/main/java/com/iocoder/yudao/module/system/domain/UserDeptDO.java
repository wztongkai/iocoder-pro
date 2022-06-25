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
 * 用户岗位表
 * </p>
 *
 * @author wu kai
 * @since 2022-06-25
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("system_user_dept")
@ApiModel(value = "UserDeptDO对象", description = "用户岗位表")
public class UserDeptDO extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户ID")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty("部门ID")
    @TableField("dept_id")
    private Long deptId;


}
