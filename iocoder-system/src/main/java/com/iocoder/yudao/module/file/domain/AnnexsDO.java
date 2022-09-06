package com.iocoder.yudao.module.file.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.iocoder.yudao.module.commons.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>
 *
 * </p>
 *
 * @author wu kai
 * @since 2022-09-06
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("system_annexs")
@ApiModel(value = "AnnexsDO对象", description = "附件表")
public class AnnexsDO extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("附件名")
    @TableField("annex_name")
    private String annexName;

    @ApiModelProperty("附件code")
    @TableField("annex_code")
    private String annexCode;

    @ApiModelProperty("附件地址")
    @TableField("annex_address")
    private String annexAddress;

    @ApiModelProperty("附件内容")
    @TableField("annex_content")
    private String annexContent;

    @ApiModelProperty("文档类型")
    @TableField("annex_type")
    private Integer annexType;

    @ApiModelProperty("备注")
    @TableField("remark")
    private String remark;


}
