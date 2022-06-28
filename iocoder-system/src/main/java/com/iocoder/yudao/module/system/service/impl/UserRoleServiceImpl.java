package com.iocoder.yudao.module.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iocoder.yudao.module.commons.core.LambdaQueryWrapperX;
import com.iocoder.yudao.module.commons.utils.convert.CollConvertUtils;
import com.iocoder.yudao.module.system.domain.RoleDO;
import com.iocoder.yudao.module.system.domain.UserRoleDO;
import com.iocoder.yudao.module.system.mapper.UserRoleMapper;
import com.iocoder.yudao.module.system.service.RoleService;
import com.iocoder.yudao.module.system.service.UserRoleService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Set;

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

    @Resource
    RoleService roleService;

    @Override
    public List<RoleDO> selectRoleInfoByUserId(Long userId) {
        // 根据用户编号查询用户角色信息
        List<UserRoleDO> userRoleList = baseMapper.selectList(new LambdaQueryWrapperX<UserRoleDO>()
                .eqIfPresent(UserRoleDO::getUserId, userId)
                .orderByDesc(UserRoleDO::getCreateTime)
        );
        if (CollectionUtils.isEmpty(userRoleList)) {
            return Collections.emptyList();
        }
        // 获取角色编号集合
        Set<Long> roleId = CollConvertUtils.convertSet(userRoleList, UserRoleDO::getRoleId);
        // 查询角色信息集合
        return roleService.getRoleInfoList(roleId);
    }
}
