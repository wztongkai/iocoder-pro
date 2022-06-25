package com.iocoder.yudao.module.system.service;

import com.iocoder.yudao.module.system.domain.DeptDO;
import com.iocoder.yudao.module.system.domain.UserDeptDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户岗位表 服务类
 * </p>
 *
 * @author wu kai
 * @since 2022-06-25
 */
public interface UserDeptService extends IService<UserDeptDO> {

    /**
     * 查询用户的部门信息集合
     *
     * @param userId 用户编号
     * @return 用户部门信息集合
     */
    List<DeptDO> selectDeptInfoByUserId(Long userId);
}
