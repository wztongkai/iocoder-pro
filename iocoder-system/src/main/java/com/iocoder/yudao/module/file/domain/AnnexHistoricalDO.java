package com.iocoder.yudao.module.file.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.iocoder.yudao.module.commons.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>
 * 附件历史版本表
 * </p>
 *
 * @author wu kai
 * @since 2022-09-06
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("system_annex_historical")
@ApiModel(value = "AnnexHistoricalDO对象", description = "附件历史版本表")
public class AnnexHistoricalDO extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("附件编号")
    @TableField("annex_id")
    private Long annexId;

    @ApiModelProperty("附件名")
    @TableField("annex_name")
    private String annexName;

    @ApiModelProperty("附件地址")
    @TableField("annex_address")
    private String annexAddress;

    @ApiModelProperty("附件版本")
    @TableField("annex_version")
    private String annexVersion;

    @ApiModelProperty("文档类型")
    @TableField("annex_type")
    private Integer annexType;

    @ApiModelProperty("备注")
    @TableField("remark")
    private String remark;


}
