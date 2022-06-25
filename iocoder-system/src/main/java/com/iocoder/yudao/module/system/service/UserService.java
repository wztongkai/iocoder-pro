package com.iocoder.yudao.module.system.service;

import com.iocoder.yudao.module.commons.core.domain.PageResult;
import com.iocoder.yudao.module.commons.core.domain.UserDO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.iocoder.yudao.module.system.vo.user.*;

import java.util.List;

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

    /**
     * 创建用户
     *
     * @param reqVO 用户信息
     * @return 用户编号
     */
    Long createUser(UserCreateReqVO reqVO);

    /**
     * 修改用户
     *
     * @param reqVO 用户信息
     */
    void updateUser(UserUpdateReqVO reqVO);

    /**
     * 删除用户
     *
     * @param userId 用户编号
     */
    void deleteUser(Long userId);

    /**
     * 修改用户密码
     *
     * @param userId   用户编号
     * @param password 密码
     */
    void updateUserPassword(Long userId, String password);

    /**
     * 修改用户状态
     * @param userId 用户编号
     * @param status 状态
     */
    void updateUserStatus(Long userId, Integer status);

    /**
     * 获取启用状态的用户信息
     * @param status 状态
     * @return 用户信息列表
     */
    List<UserSimpleRespVO> getUsersByStatus(Integer status);

    /**
     * 查询用户详情
     * @param userId 用户编号
     * @return 用户详情
     */
    UserRespVO getUserInfo(Long userId);
}
