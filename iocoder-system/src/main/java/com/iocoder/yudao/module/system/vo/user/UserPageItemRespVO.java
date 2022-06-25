package com.iocoder.yudao.module.system.vo.user;

import com.iocoder.yudao.module.system.domain.DeptDO;
import com.iocoder.yudao.module.system.domain.PostDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@ApiModel(value = "管理后台 - 用户分页时的信息 Response VO", description = "相比用户基本信息来说，会多部门信息")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserPageItemRespVO extends UserRespVO {

    @ApiModelProperty(value = "部门信息列表")
    private List<DeptDO> deptList;

    @ApiModelProperty(value = "岗位信息列表")
    private List<PostDO> postList;

}
