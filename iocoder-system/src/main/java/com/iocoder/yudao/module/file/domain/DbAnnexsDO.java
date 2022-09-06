package com.iocoder.yudao.module.file.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.iocoder.yudao.module.commons.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>
 * 数据库附件表
 * </p>
 *
 * @author wu kai
 * @since 2022-09-06
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("system_db_annexs")
@ApiModel(value = "DbAnnexsDO对象", description = "数据库附件表")
public class DbAnnexsDO extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("数据库附件编号")
    @TableField("db_annex_id")
    private Long dbAnnexId;

    @ApiModelProperty("数据库名")
    @TableField("db_name")
    private String dbName;

    @ApiModelProperty("文档类型")
    @TableField("annex_type")
    private Integer annexType;

    @ApiModelProperty("备注")
    @TableField("remark")
    private String remark;


}
