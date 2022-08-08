package com.iocoder.yudao.module.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iocoder.yudao.module.commons.core.LambdaQueryWrapperX;
import com.iocoder.yudao.module.commons.core.domain.PageResult;
import com.iocoder.yudao.module.commons.enums.common.CommonStatusEnum;
import com.iocoder.yudao.module.commons.enums.role.DataScopeEnum;
import com.iocoder.yudao.module.commons.enums.role.RoleCodeEnum;
import com.iocoder.yudao.module.commons.enums.role.RoleTypeEnum;
import com.iocoder.yudao.module.commons.exception.ServiceExceptionUtil;
import com.iocoder.yudao.module.commons.utils.BeanUtil;
import com.iocoder.yudao.module.system.domain.RoleDO;
import com.iocoder.yudao.module.system.domain.RoleMenuDO;
import com.iocoder.yudao.module.system.domain.UserRoleDO;
import com.iocoder.yudao.module.system.mapper.RoleMapper;
import com.iocoder.yudao.module.system.service.RoleMenuService;
import com.iocoder.yudao.module.system.service.RoleService;
import com.iocoder.yudao.module.system.service.UserRoleService;
import com.iocoder.yudao.module.system.vo.permission.role.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

import static com.iocoder.yudao.module.commons.constant.ErrorCodeConstants.REQ_ARGS_NOT_NULL;
import static com.iocoder.yudao.module.commons.constant.ErrorCodeConstants.RoleErrorCode.*;
import static com.iocoder.yudao.module.commons.exception.ServiceExceptionUtil.exception;

/**
 * <p>
 * 角色信息表 服务实现类
 * </p>
 *
 * @author wu kai
 * @since 2022-06-22
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, RoleDO> implements RoleService {

    @Resource
    RoleMenuService roleMenuService;

    @Resource
    UserRoleService userRoleService;

    @Override
    public Long createRole(RoleCreateReqVO createReqVO) {
        // 校验参数
        checkCreateOrUpdate(null, createReqVO.getName(), createReqVO.getCode());
        // 构建新增数据
        RoleDO roleDO = new RoleDO();
        BeanUtil.copyProperties(createReqVO, roleDO);
        roleDO.setType(ObjectUtil.defaultIfNull(null, RoleTypeEnum.CUSTOM.getType()));
        roleDO.setStatus(CommonStatusEnum.ENABLE.getStatus());
        roleDO.setDataScope(DataScopeEnum.ALL.getScope());
        // 执行新增操作
        baseMapper.insert(roleDO);
        return roleDO.getId();
    }

    @Override
    public void updateRole(RoleUpdateReqVO updateReqVO) {
        // 校验是否为系统内置角色
        checkUpdateRoleIsSystem(updateReqVO.getId());
        // 校验参数
        checkCreateOrUpdate(updateReqVO.getId(), updateReqVO.getName(), updateReqVO.getCode());
        // 校验通过，执行更新操作
        RoleDO roleDO = new RoleDO();
        BeanUtil.copyProperties(updateReqVO, roleDO);
        baseMapper.updateById(roleDO);
    }

    @Override
    public void updateRoleStatus(RoleUpdateStatusReqVO updateStatusReqVO) {
        // 校验是否为系统内置角色
        checkUpdateRoleIsSystem(updateStatusReqVO.getId());
        // 校验通过，执行更新操作
        RoleDO roleDO = new RoleDO();
        BeanUtil.copyProperties(updateStatusReqVO, roleDO);
        baseMapper.updateById(roleDO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRole(Long roleId) {
        // 校验是否为系统内置角色
        checkUpdateRoleIsSystem(roleId);
        // 校验通过，执行删除
        baseMapper.deleteById(roleId);
        // 删除角色菜单
        roleMenuService.remove(new LambdaUpdateWrapper<RoleMenuDO>()
                .eq(RoleMenuDO::getRoleId, roleId)
        );
        // 删除用户角色
        userRoleService.remove(new LambdaUpdateWrapper<UserRoleDO>()
                .eq(UserRoleDO::getRoleId, roleId)
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRoleBatch(RoleBatchDeleteReqVO batchDeleteReqVO) {
        if(CollectionUtils.isEmpty(batchDeleteReqVO.getRoleIds())){
            throw ServiceExceptionUtil.exception(REQ_ARGS_NOT_NULL);
        }
        batchDeleteReqVO.getRoleIds().forEach(this::deleteRole);
    }

    @Override
    public RoleRespVO getRoleInfo(Long roleId) {
        RoleDO roleDO = baseMapper.selectById(roleId);
        RoleRespVO roleRespVO = new RoleRespVO();
        BeanUtil.copyProperties(roleDO, roleRespVO);
        return roleRespVO;
    }

    @Override
    public PageResult<RoleDO> getRolePage(RolePageReqVO reqVO) {
        return baseMapper.getRolePage(reqVO);
    }

    @Override
    public List<RoleDO> getRoles(Collection<Integer> statuses) {
        if (CollectionUtils.isEmpty(statuses)) {
            return baseMapper.selectList(new LambdaQueryWrapperX<RoleDO>()
                    .orderByDesc(RoleDO::getSort)
            );
        }
        return baseMapper.selectList(new LambdaQueryWrapperX<RoleDO>()
                .inIfPresent(RoleDO::getStatus, statuses)
                .orderByDesc(RoleDO::getSort)
        );
    }

    @Override
    public List<RoleDO> getSimpleRoleInfos(Set<Long> roleId) {
        List<RoleDO> roleList = baseMapper.selectBatchIds(roleId);
        if (CollectionUtils.isEmpty(roleList)) {
            return Collections.emptyList();
        }
        return roleList;
    }

    @Override
    public Set<String> selectRolePermissionByUserId(Long userId) {
        List<RoleDO> roleList = userRoleService.selectRoleInfoByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (RoleDO perm : roleList)
        {
            if (ObjectUtils.isNotEmpty(perm))
            {
                permsSet.addAll(Arrays.asList(perm.getCode().trim().split(",")));
            }
        }
        return permsSet;
    }

    @Override
    public boolean hasAnySuperAdmin(List<RoleDO> roleInfoList) {
        if (CollectionUtil.isEmpty(roleInfoList)) {
            return false;
        }
        return roleInfoList.stream().anyMatch(role -> RoleCodeEnum.isSuperAdmin(role.getCode()));
    }


    /**
     * 是否为系统内置角色
     *
     * @param id 角色编号
     */
    public void checkUpdateRoleIsSystem(Long id) {
        RoleDO roleDO = baseMapper.selectById(id);
        if (ObjectUtils.isEmpty(roleDO)) {
            throw exception(ROLE_NOT_EXISTS);
        }
        // 内置角色，不允许操作
        if (RoleTypeEnum.SYSTEM.getType().equals(roleDO.getType())) {
            throw exception(ROLE_CAN_NOT_UPDATE_SYSTEM_TYPE_ROLE);
        }
    }

    /**
     * 校验参数
     *
     * @param id   角色编号
     * @param name 角色名称
     * @param code 角色code
     */
    private void checkCreateOrUpdate(Long id, String name, String code) {
        // 校验角色名是否唯一
        if (StringUtils.isBlank(name)) {
            return;
        }
        RoleDO roleDO = baseMapper.selectOne(new LambdaQueryWrapperX<RoleDO>()
                .eqIfPresent(RoleDO::getName, name)
                .orderByDesc(RoleDO::getCreateTime)
                .last("limit 1")
        );
        if (ObjectUtils.isNotEmpty(roleDO) && Objects.equals(roleDO.getId(), id)) {
            throw exception(ROLE_NAME_DUPLICATE, name);
        }
        // 校验角色编码是否唯一
        if (StringUtils.isBlank(code)) {
            return;
        }
        roleDO = baseMapper.selectOne(new LambdaQueryWrapperX<RoleDO>()
                .eqIfPresent(RoleDO::getCode, code)
                .orderByDesc(RoleDO::getCreateTime)
                .last("limit 1")
        );
        if (ObjectUtils.isNotEmpty(roleDO) && Objects.equals(roleDO.getId(), id)) {
            throw exception(ROLE_CODE_DUPLICATE, name);
        }

    }
}
