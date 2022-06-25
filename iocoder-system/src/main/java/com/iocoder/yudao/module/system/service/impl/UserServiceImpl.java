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
import com.iocoder.yudao.module.system.domain.UserDeptDO;
import com.iocoder.yudao.module.system.domain.UserPostDO;
import com.iocoder.yudao.module.system.mapper.UserMapper;
import com.iocoder.yudao.module.system.service.*;
import com.iocoder.yudao.module.system.vo.user.UserCreateReqVO;
import com.iocoder.yudao.module.system.vo.user.UserPageQueryRequestVo;
import com.iocoder.yudao.module.system.vo.user.UserUpdateReqVO;
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
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
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
        checkUserIdExist(id);
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
    public void checkUserIdExist(Long id) {
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


}
