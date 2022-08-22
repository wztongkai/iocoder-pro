package com.iocoder.yudao.module.system.vo.user;

import com.iocoder.yudao.module.system.domain.RoleDO;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author wu kai
 * @since 2022/8/22
 */

@ApiModel("用户角色列表 Response VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRoleRespVO {
    // 用户编号
    private Long userId;
    // 用户角色列表
    private List<RoleDO> userRoleList;
}
