package com.iocoder.yudao.module.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iocoder.yudao.module.commons.core.domain.PageResult;
import com.iocoder.yudao.module.system.domain.RoleDO;
import com.iocoder.yudao.module.system.vo.permission.role.*;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.lang.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 角色信息表 服务类
 * </p>
 *
 * @author wu kai
 * @since 2022-06-22
 */
public interface RoleService extends IService<RoleDO> {

    /**
     * 新增角色信息
     *
     * @param createReqVO 角色信息
     * @return 角色编号
     */
    Long createRole(RoleCreateReqVO createReqVO);

    /**
     * 修改角色信息
     *
     * @param updateReqVO 角色信息
     */
    void updateRole(RoleUpdateReqVO updateReqVO);

    /**
     * 修改角色状态
     *
     * @param updateStatusReqVO 角色状态信息
     */
    void updateRoleStatus(RoleUpdateStatusReqVO updateStatusReqVO);

    /**
     * 删除角色
     *
     * @param roleId 角色编号
     */
    void deleteRole(Long roleId);

    /**
     * 获取角色详情
     *
     * @param roleId 角色编号
     * @return 角色信息
     */
    RoleRespVO getRoleInfo(Long roleId);

    /**
     * 分页获取角色列表
     *
     * @param reqVO 分页信息
     * @return 角色列表
     */
    PageResult<RoleDO> getRolePage(RolePageReqVO reqVO);

    /**
     * 获得角色列表
     *
     * @param statuses 筛选的状态。允许空，空时不筛选
     * @return 角色列表
     */
    List<RoleDO> getRoles(@Nullable Collection<Integer> statuses);


    /**
     * 查询角色信息集合
     *
     * @param roleId 角色编号集合
     * @return 角色信息集合
     */
    default List<RoleDO> getRoleInfoList(Set<Long> roleId) {
        if (CollectionUtils.isEmpty(roleId)) {
            return Collections.emptyList();
        }
        // 获取角色信息集合
        return getSimpleRoleInfos(roleId);
    }

    /**
     * 获取角色信息集合
     *
     * @param roleId 角色编号数组
     * @return 角色信息集合
     */
    List<RoleDO> getSimpleRoleInfos(Set<Long> roleId);
}
