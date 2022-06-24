package com.iocoder.yudao.module.system.service;

import com.iocoder.yudao.module.commons.core.domain.PageResult;
import com.iocoder.yudao.module.commons.core.domain.UserDO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.iocoder.yudao.module.system.vo.user.UserPageQueryRequestVo;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author wu kai
 * @since 2022-06-22
 */
public interface UserService extends IService<UserDO> {

    /**
     * 根据条件分页查询用户列表
     *
     * @param requestVo 用户信息
     * @return 用户信息集合
     */
    PageResult<UserDO> selectUserList(UserPageQueryRequestVo requestVo);


    /**
     * 修改用户登录信息
     *
     * @param userId  用户编号
     * @param loginIp 登录ip
     */
    void updateUserLogin(Long userId, String loginIp);
}
