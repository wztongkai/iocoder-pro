package com.iocoder.yudao.module.system.vo.user;

import com.iocoder.yudao.module.system.domain.DeptDO;
import com.iocoder.yudao.module.system.domain.PostDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@ApiModel(value = "管理后台 - 用户分页时的信息 Response VO", description = "相比用户基本信息来说，会多部门信息")
@Data
@EqualsAndHashCode(callSuper = true)
public class UserPageItemRespVO extends UserRespVO {



}
