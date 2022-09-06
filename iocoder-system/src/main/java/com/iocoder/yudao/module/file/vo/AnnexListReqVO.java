package com.iocoder.yudao.module.file.vo;

import com.iocoder.yudao.module.commons.core.domain.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author wu kai
 * @since 2022/9/6
 */

@EqualsAndHashCode(callSuper = true)
@ApiModel("管理后台 - 附件列表 Request VO")
@Data
public class AnnexListReqVO extends PageParam {

    @ApiModelProperty(value = "搜索", example = "项目经理/ceo", notes = "岗位编号或岗位名称模糊匹配")
    private String search;

    @ApiModelProperty(value = "创建时间", example = "[2022-07-01 00:00:00,2022-07-01 23:59:59]")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date[] createTime;
}
