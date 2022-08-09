package com.iocoder.yudao.module.system.vo;

import com.iocoder.yudao.module.commons.core.domain.UserDO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * @author wu kai
 * @since 2022/8/8
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfoReqsVo {
    private UserDO user;
    private Set<String> role;
    private Set<String> permissions;

}
