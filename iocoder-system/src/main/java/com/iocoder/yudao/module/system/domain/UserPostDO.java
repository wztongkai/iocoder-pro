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
 * 用户岗位表
 * </p>
 *
 * @author wu kai
 * @since 2022-06-22
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("system_user_post")
@ApiModel(value = "UserPostDO对象", description = "用户岗位表")
public class UserPostDO extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户ID")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty("岗位ID")
    @TableField("post_id")
    private Long postId;


}
