package com.iocoder.yudao.module.system.vo.annex;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wu kai
 * @since 2022/10/20
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "附件信息 Response vo")
public class AnnexsSimpleRespVO {

    @ApiModelProperty(value = "文件名")
    private String fileName;

    @ApiModelProperty(value = "文件链接")
    private String filePath;

    @ApiModelProperty(value = "文件类型")
    private Integer fileType;
}
