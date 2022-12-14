package com.iocoder.yudao.module.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iocoder.yudao.module.commons.core.domain.PageResult;
import com.iocoder.yudao.module.commons.core.domain.UserDO;
import com.iocoder.yudao.module.system.vo.user.*;
import com.iocoder.yudao.module.system.vo.user.profile.UserProfileUpdatePasswordReqVO;
import com.iocoder.yudao.module.system.vo.user.profile.UserProfileUpdateReqVO;

import javax.validation.Valid;
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
     * 用户批量删除
     *
     * @param batchDeleteReqVO 用户编号集合
     */
    void deleteUserBatch(UserBatchDeleteReqVO batchDeleteReqVO);

    /**
     * 修改用户密码
     *
     * @param userId   用户编号
     * @param password 密码
     */
    void updateUserPassword(Long userId, String password);

    /**
     * 修改用户个人密码
     *
     * @param id    用户编号
     * @param reqVO 更新用户个人密码
     */
    void updateUserPassword(Long id, @Valid UserProfileUpdatePasswordReqVO reqVO);

    /**
     * 修改用户状态
     *
     * @param userId 用户编号
     * @param status 状态
     */
    void updateUserStatus(Long userId, Integer status);

    /**
     * 获取启用状态的用户信息
     *
     * @param status 状态
     * @return 用户信息列表
     */
    List<UserSimpleRespVO> getUsersByStatus(Integer status);

    /**
     * 查询用户详情
     *
     * @param userId 用户编号
     * @return 用户详情
     */
    UserRespVO getUserInfo(Long userId);

    /**
     * 修改用户个人信息
     *
     * @param loginUserId 用户编号
     * @param reqVO       用户个人信息
     */
    void updateUserProfile(Long loginUserId, UserProfileUpdateReqVO reqVO);

    /**
     * 更新用户头像
     *
     * @param loginUserId 用户 id
     * @param avatar      头像地址
     */
    boolean updateUserAvatar(Long loginUserId, String avatar);

    /**
     * 根据用户昵称模糊查询
     *
     * @param userNickname 用户昵称
     * @return 用户列表
     */
    List<UserDO> selectUserByNickName(String userNickname);

    /**
     * 根据用户编号查询用户拥有的角色列表
     *
     * @param userId 用户编号
     * @return 用户拥有的角色列表
     */
    UserRoleRespVO getUserRoleList(Long userId);

    /**
     * 获取用户名的拼音
     *
     * @param username 用户名
     * @return 用户名拼音
     */
    String[] getUserNamePinyin(String username);

    /**
     * 根据用户名获取 姓氏 和 姓名
     *
     * @param username 用户名
     * @return 姓氏、姓名
     */
    UserNameSplitRespVO getUserLastNameAndFirstName(String username);

    /**
     * 获取用户姓名电码
     *
     * @param username 用户名
     * @return 姓名电码
     */
    String getUserNameElectronicCode(String username);
}
