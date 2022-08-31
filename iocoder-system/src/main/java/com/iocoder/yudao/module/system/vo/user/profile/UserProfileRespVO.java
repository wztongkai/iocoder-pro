package com.iocoder.yudao.module.system.vo.user.profile;

import com.iocoder.yudao.module.system.domain.DeptDO;
import com.iocoder.yudao.module.system.domain.PostDO;
import com.iocoder.yudao.module.system.vo.user.UserBaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @Author: kai wu
 * @Date: 2022/6/26 14:29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("管理后台 - 用户个人中心信息 Response VO")
public class UserProfileRespVO extends UserBaseVO {
    @ApiModelProperty(value = "用户编号", required = true, example = "1")
    private Long id;

    @ApiModelProperty(value = "状态", required = true, example = "1", notes = "CommonStatusEnum 枚举类")
    private Integer status;

    @ApiModelProperty(value = "最后登录 IP", required = true, example = "192.168.1.1")
    private String loginIp;

    @ApiModelProperty(value = "最后登录时间", required = true, example = "时间戳格式")
    private Date loginDate;

    @ApiModelProperty(value = "创建时间", required = true, example = "时间戳格式")
    private Date createTime;

    /**
     * 所属角色
     */
//    private List<Role> roles;

    /**
     * 所在部门
     */
    private List<DeptDO> deptList;

    /**
     * 所属岗位数组
     */
    private List<PostDO> postList;
}
