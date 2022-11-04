package com.iocoder.yudao.module.system.domain.core;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.iocoder.yudao.module.system.vo.dept.DeptRespVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author wu kai
 * @since 2022/11/4
 */

@Data
public class DataTree implements Serializable {

    @ApiModelProperty(value = "节点Id")
    private Long id;

    @ApiModelProperty(value = "节点名称")
    private String label;

    @ApiModelProperty(value = "子节点")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<DataTree> children;

    public DataTree(DeptRespVO deptRespVO) {
        this.id = deptRespVO.getId();
        this.label = deptRespVO.getName();
        this.children = deptRespVO.getChildren().stream().filter(Objects::nonNull).map(DataTree::new).collect(Collectors.toList());
    }

}
