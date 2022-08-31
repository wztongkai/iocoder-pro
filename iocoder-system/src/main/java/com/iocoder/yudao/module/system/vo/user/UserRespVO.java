package com.iocoder.yudao.module.system.vo.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.iocoder.yudao.module.system.domain.DeptDO;
import com.iocoder.yudao.module.system.domain.PostDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@ApiModel("管理后台 - 用户信息 Response VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserRespVO extends UserBaseVO {

    @ApiModelProperty(value = "用户编号", required = true, example = "1")
    private Long id;

    @ApiModelProperty(value = "状态", required = true, example = "1", notes = "CommonStatusEnum 枚举类")
    private Integer status;

    @ApiModelProperty(value = "最后登录 IP", required = true, example = "192.168.1.1")
    private String loginIp;

    @ApiModelProperty(value = "最后登录时间", required = true, example = "时间戳格式")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDateTime loginDate;

    @ApiModelProperty(value = "创建时间", required = true, example = "时间戳格式")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "部门信息列表")
    private List<DeptDO> deptList;

    @ApiModelProperty(value = "岗位信息列表")
    private List<PostDO> postList;

//    @ApiModelProperty(value = "角色信息列表")
//    private List<RoleDO> roleList;

}
