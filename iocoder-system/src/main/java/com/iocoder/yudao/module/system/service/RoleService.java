package com.iocoder.yudao.module.system.service;

import com.iocoder.yudao.module.system.domain.RoleDO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.iocoder.yudao.module.system.vo.role.RoleCreateReqVO;
import com.iocoder.yudao.module.system.vo.role.RoleUpdateReqVO;

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
     * @param updateReqVO 角色信息
     */
    void updateRole(RoleUpdateReqVO updateReqVO);
}
