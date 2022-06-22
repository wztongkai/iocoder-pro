package com.iocoder.yudao.commons.core.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.apache.ibatis.type.JdbcType;

import java.time.LocalDateTime;

/**
 * 实体类通用父类
 *
 * @Author: kai wu
 * @Date: 2022/6/11 03:03
 */
@Data
@FieldNameConstants
public class BaseEntity {

    @ApiModelProperty("ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty("创建者")
    @TableField(fill = FieldFill.INSERT, jdbcType = JdbcType.VARCHAR)
    private String creator;

    @ApiModelProperty("创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("更新者")
    @TableField(fill = FieldFill.INSERT, jdbcType = JdbcType.VARCHAR)
    private String updater;

    @ApiModelProperty("更新时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @ApiModelProperty("是否删除")
    @TableField("deleted")
    @TableLogic
    private Boolean deleted;
}
