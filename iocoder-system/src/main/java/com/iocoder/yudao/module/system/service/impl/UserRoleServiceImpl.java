package com.iocoder.yudao.module.system.service.impl;

import com.iocoder.yudao.module.system.domain.UserRoleDO;
import com.iocoder.yudao.module.system.mapper.UserRoleMapper;
import com.iocoder.yudao.module.system.service.UserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户和角色关联表 服务实现类
 * </p>
 *
 * @author wu kai
 * @since 2022-06-22
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRoleDO> implements UserRoleService {

}
