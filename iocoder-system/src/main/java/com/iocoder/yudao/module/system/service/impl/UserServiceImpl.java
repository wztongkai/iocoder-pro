package com.iocoder.yudao.module.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iocoder.yudao.module.commons.core.LambdaQueryWrapperX;
import com.iocoder.yudao.module.commons.core.domain.PageResult;
import com.iocoder.yudao.module.commons.core.domain.UserDO;
import com.iocoder.yudao.module.commons.enums.CommonStatusEnum;
import com.iocoder.yudao.module.commons.exception.ServiceExceptionUtil;
import com.iocoder.yudao.module.commons.utils.BeanUtil;
import com.iocoder.yudao.module.commons.utils.convert.CollConvertUtils;
import com.iocoder.yudao.module.system.domain.DeptDO;
import com.iocoder.yudao.module.system.domain.PostDO;
import com.iocoder.yudao.module.system.domain.UserDeptDO;
import com.iocoder.yudao.module.system.domain.UserPostDO;
import com.iocoder.yudao.module.system.mapper.UserMapper;
import com.iocoder.yudao.module.system.service.*;
import com.iocoder.yudao.module.system.vo.user.*;
import com.iocoder.yudao.module.system.vo.user.profile.UserProfileUpdatePasswordReqVO;
import com.iocoder.yudao.module.system.vo.user.profile.UserProfileUpdateReqVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.iocoder.yudao.module.commons.constant.ErrorCodeConstants.UserErrorCode.*;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author wu kai
 * @since 2022-06-22
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {

    @Resource
    DeptService deptService;

    @Resource
    PostService postService;

    @Resource
    PasswordEncoder passwordEncoder;

    @Resource
    UserDeptService userDeptService;

    @Resource
    UserPostService userPostService;

    @Resource
    UserService userService;

    @Override
    public PageResult<UserDO> selectUserList(UserPageQueryRequestVo requestVo) {

        return baseMapper.selectUserList(requestVo);
    }

    @Override
    public void updateUserLogin(Long userId, String loginIp) {
        baseMapper.update(null, new LambdaUpdateWrapper<UserDO>()
                .set(UserDO::getLoginIp, loginIp)
                .set(UserDO::getLoginDate, LocalDate.now())
                .eq(UserDO::getId, userId)
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createUser(UserCreateReqVO reqVO) {
        // 参数校验
        checkCreateOrUpdate(null, reqVO.getUsername(), reqVO.getMobile(), reqVO.getEmail(), reqVO.getDeptIds(),
                reqVO.getPostIds());
        // 插入用户基本信息
        UserDO userDO = new UserDO();
        BeanUtil.copyProperties(reqVO, userDO);
        userDO.setStatus(CommonStatusEnum.ENABLE.getStatus());
        userDO.setPassword(passwordEncoder.encode(reqVO.getPassword()));
        baseMapper.insert(userDO);
        // 插入部门信息
        if (CollectionUtils.isNotEmpty(reqVO.getDeptIds())) {
            reqVO.getDeptIds().forEach(deptId -> {
                userDeptService.save(new UserDeptDO().setUserId(userDO.getId()).setDeptId(deptId));
            });
        }
        // 插入岗位信息
        if (CollectionUtils.isNotEmpty(reqVO.getPostIds())) {
            reqVO.getPostIds().forEach(postId -> {
                userPostService.save(new UserPostDO().setUserId(userDO.getId()).setPostId(postId));
            });
        }
        return userDO.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(UserUpdateReqVO reqVO) {
        // 参数校验
        checkCreateOrUpdate(reqVO.getId(), reqVO.getUsername(), reqVO.getMobile(), reqVO.getEmail(), reqVO.getDeptIds(),
                reqVO.getPostIds());

        // 修改用户基本信息
        UserDO userDO = new UserDO();
        BeanUtil.copyProperties(reqVO, userDO);
        baseMapper.updateById(userDO);
        // 修改用户部门信息
        updateUserDeptInfo(reqVO);
        // 修改用户岗位信息
        updateUserPostInfo(reqVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(Long userId) {
        // 校验用户存在
        checkUserExist(userId);
        // 删除用户基本信息
        userService.removeById(userId);
        // 删除用户部门信息
        userDeptService.remove(new LambdaUpdateWrapper<UserDeptDO>()
                .in(UserDeptDO::getUserId, userId)
        );
        // 删除用户岗位信息
        userPostService.remove(new LambdaUpdateWrapper<UserPostDO>()
                .in(UserPostDO::getUserId, userId)
        );
    }

    @Override
    public void updateUserPassword(Long userId, String password) {
        // 校验用户存在
        checkUserExist(userId);
        // 更新密码
        UserDO updateObj = new UserDO();
        updateObj.setId(userId);
        updateObj.setPassword(passwordEncoder.encode(password));
        userService.updateById(updateObj);
    }

    @Override
    public void updateUserPassword(Long id, UserProfileUpdatePasswordReqVO reqVO) {
        // 校验旧密码密码
        checkOldPassword(id, reqVO.getOldPassword());
        // 旧密码校验通过，执行修改
        UserDO updateObj = new UserDO();
        updateObj.setId(id);
        updateObj.setPassword(passwordEncoder.encode(reqVO.getNewPassword()));
        userService.updateById(updateObj);
    }

    @Override
    public void updateUserStatus(Long userId, Integer status) {
        // 校验用户存在
        checkUserExist(userId);
        // 更新密码
        UserDO updateObj = new UserDO();
        updateObj.setId(userId);
        updateObj.setStatus(status);
        userService.updateById(updateObj);
    }

    @Override
    public List<UserSimpleRespVO> getUsersByStatus(Integer status) {
        List<UserDO> userList = baseMapper.selectList(new LambdaQueryWrapperX<UserDO>()
                .eqIfPresent(UserDO::getStatus, status)
                .orderByDesc(UserDO::getCreateTime)
        );
        List<UserSimpleRespVO> simpleRespList = new ArrayList<>();
        BeanUtil.copyListProperties(userList, simpleRespList, UserSimpleRespVO.class);
        return simpleRespList;
    }

    @Override
    public UserRespVO getUserInfo(Long userId) {
        // 校验用户存在
        checkUserExist(userId);
        // 查询用户基本信息
        UserDO userDO = baseMapper.selectById(userId);
        if (ObjectUtils.isEmpty(userDO)) {
            return null;
        }
        UserRespVO userRespVO = new UserRespVO();
        BeanUtil.copyProperties(userDO, userRespVO);
        // 查询用户部门信息
        List<DeptDO> userDeptInfoList = userDeptService.selectDeptInfoByUserId(userId);
        if (CollectionUtils.isNotEmpty(userDeptInfoList)) {
            userRespVO.setUserDeptInfoList(userDeptInfoList);
        }
        // 查询用户岗位信息
        List<PostDO> userPostInfoList = userPostService.selectPostInfoByUserId(userId);
        if (CollectionUtils.isNotEmpty(userPostInfoList)) {
            userRespVO.setUserPostInfoList(userPostInfoList);
        }
        return userRespVO;
    }

    @Override
    public void updateUserProfile(Long loginUserId, UserProfileUpdateReqVO reqVO) {
        // 校验用户存在
        checkUserExist(loginUserId);
        // 校验手机号唯一
        checkMobileUnique(loginUserId, reqVO.getMobile());
        // 校验邮箱唯一
        checkEmailUnique(loginUserId, reqVO.getEmail());
        UserDO userDO = new UserDO();
        BeanUtil.copyProperties(reqVO, userDO);
        userDO.setId(loginUserId);
        // 校验通过，更新用户信息
        baseMapper.updateById(userDO);
    }

    @Override
    public String updateUserAvatar(Long loginUserId, InputStream avatarFile) {
        // 校验用户存在
        checkUserExist(loginUserId);
        return null;
    }


    /**
     * 修改用户部门信息
     *
     * @param reqVO 用户信息
     */
    private void updateUserDeptInfo(UserUpdateReqVO reqVO) {

        Set<Long> dbDeptIds = CollConvertUtils.convertSet(userDeptService.selectUserDeptListByUserId(reqVO.getId()),
                UserDeptDO::getDeptId);
        // 计算新增和删除的部门编号
        Set<Long> deptIds = reqVO.getDeptIds().stream().filter(Objects::nonNull).collect(Collectors.toSet());
        Collection<Long> createDeptIds = CollUtil.subtract(deptIds, dbDeptIds);
        Collection<Long> deleteDeptIds = CollUtil.subtract(dbDeptIds, deptIds);
        // 执行新增和删除
        if (CollectionUtils.isNotEmpty(createDeptIds)) {
            createDeptIds.forEach(deptId -> {
                userDeptService.save(new UserDeptDO().setUserId(reqVO.getId()).setDeptId(deptId));
            });
        }
        if (CollectionUtils.isNotEmpty(deleteDeptIds)) {
            deleteDeptIds.forEach(deptId -> {
                userDeptService.remove(new LambdaUpdateWrapper<UserDeptDO>()
                        .eq(UserDeptDO::getUserId, reqVO.getId())
                        .in(UserDeptDO::getDeptId, deptId)
                );
            });
        }
    }

    /**
     * 修改用户岗位信息
     *
     * @param reqVO 用户信息
     */
    private void updateUserPostInfo(UserUpdateReqVO reqVO) {
        Set<Long> dbPostIds = CollConvertUtils.convertSet(userPostService.selectUserPostListByUserId(reqVO.getId()),
                UserPostDO::getPostId);
        // 计算新增和删除的岗位编号
        Set<Long> postIds = reqVO.getPostIds().stream().filter(Objects::nonNull).collect(Collectors.toSet());
        Collection<Long> createPostIds = CollUtil.subtract(postIds, dbPostIds);
        Collection<Long> deletePostIds = CollUtil.subtract(dbPostIds, postIds);
        // 执行新增和删除
        if (CollectionUtils.isNotEmpty(createPostIds)) {
            createPostIds.forEach(postId -> {
                userPostService.save(new UserPostDO().setUserId(reqVO.getId()).setPostId(postId));
            });
        }
        if (CollectionUtils.isNotEmpty(deletePostIds)) {
            deletePostIds.forEach(deptId -> {
                userPostService.remove(new LambdaUpdateWrapper<UserPostDO>()
                        .eq(UserPostDO::getUserId, reqVO.getId())
                        .in(UserPostDO::getPostId, deptId)
                );
            });
        }
    }

    private void checkCreateOrUpdate(Long id, String username,
                                     String mobile,
                                     @Email(message = "邮箱格式不正确") @Size(max = 50, message = "邮箱长度不能超过 50 个字符") String email,
                                     List<Long> deptIds, List<Long> postIds
    ) {
        // 校验用户是否存在
        checkUserExist(id);
        // 校验用户名是否唯一
        checkUsernameUnique(id, username);
        // 校验手机号唯一
        checkMobileUnique(id, mobile);
        // 校验邮箱唯一
        checkEmailUnique(id, email);
        // 校验部门是否开启
        deptService.validDepts(deptIds);
        // 校验岗位是否开启
        postService.validPosts(postIds);
    }

    /**
     * 校验用户是否存在
     *
     * @param id 用户编号
     */
    public void checkUserExist(Long id) {
        if (id == null) {
            return;
        }
        UserDO userDO = baseMapper.selectById(id);
        if (ObjectUtils.isEmpty(userDO)) {
            throw ServiceExceptionUtil.exception(USER_NOT_EXISTS);
        }
    }

    /**
     * 校验用户名是否唯一
     *
     * @param id       用户编号
     * @param username 用户名
     */
    public void checkUsernameUnique(Long id, String username) {
        if (StringUtils.isBlank(username)) {
            return;
        }
        UserDO user = baseMapper.selectOne(new LambdaQueryWrapperX<UserDO>()
                .eqIfPresent(UserDO::getUsername, username)
                .orderByDesc(UserDO::getCreateTime)
                .last("limit 1")
        );
        if (ObjectUtils.isEmpty(user)) {
            return;
        }
        if (!user.getId().equals(id)) {
            throw ServiceExceptionUtil.exception(USER_USERNAME_EXISTS);
        }
    }

    /**
     * 校验手机号是否唯一
     *
     * @param id     用户编号
     * @param mobile 手机号
     */
    public void checkMobileUnique(Long id, String mobile) {
        if (StringUtils.isBlank(mobile)) {
            return;
        }
        UserDO user = baseMapper.selectOne(new LambdaQueryWrapperX<UserDO>()
                .eqIfPresent(UserDO::getMobile, mobile)
                .orderByDesc(UserDO::getCreateTime)
                .last("limit 1")
        );
        if (ObjectUtils.isEmpty(user)) {
            return;
        }
        if (!user.getId().equals(id)) {
            throw ServiceExceptionUtil.exception(USER_MOBILE_EXISTS);
        }
    }

    /**
     * 校验邮箱是否唯一
     *
     * @param id    用户编号
     * @param email 邮箱
     */
    public void checkEmailUnique(Long id, String email) {
        if (StringUtils.isBlank(email)) {
            return;
        }
        UserDO user = baseMapper.selectOne(new LambdaQueryWrapperX<UserDO>()
                .eqIfPresent(UserDO::getEmail, email)
                .orderByDesc(UserDO::getCreateTime)
                .last("limit 1")
        );
        if (ObjectUtils.isEmpty(user)) {
            return;
        }
        if (!user.getId().equals(id)) {
            throw ServiceExceptionUtil.exception(USER_EMAIL_EXISTS);
        }
    }

    /**
     * 校验旧密码
     *
     * @param id          用户 id
     * @param oldPassword 旧密码
     */
    public void checkOldPassword(Long id, String oldPassword) {
        UserDO user = baseMapper.selectById(id);
        if (ObjectUtils.isEmpty(user)) {
            throw ServiceExceptionUtil.exception(USER_NOT_EXISTS);
        }
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw ServiceExceptionUtil.exception(USER_PASSWORD_FAILED);
        }
    }


}
