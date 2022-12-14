package com.iocoder.yudao.module.system.vo.dict.type;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@ApiModel("管理后台 - 字典类型信息 Response VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DictTypeRespVO extends DictTypeBaseVO {

    @ApiModelProperty(value = "字典类型编号", required = true, example = "1024")
    private Long id;

    @ApiModelProperty(value = "字典类型", required = true, example = "sys_common_sex")
    private String type;

    @ApiModelProperty(value = "创建时间", required = true, example = "时间戳格式")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

}
