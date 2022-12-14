package com.iocoder.yudao.module.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iocoder.yudao.module.commons.config.Assertion;
import com.iocoder.yudao.module.commons.core.LambdaQueryWrapperX;
import com.iocoder.yudao.module.commons.core.domain.PageResult;
import com.iocoder.yudao.module.commons.core.domain.UserDO;
import com.iocoder.yudao.module.commons.enums.common.CommonStatusEnum;
import com.iocoder.yudao.module.commons.exception.ServiceExceptionUtil;
import com.iocoder.yudao.module.commons.utils.ArrayUtils;
import com.iocoder.yudao.module.commons.utils.BeanUtil;
import com.iocoder.yudao.module.commons.utils.ChineseCharConvertUtil;
import com.iocoder.yudao.module.commons.utils.NameSplitUtils;
import com.iocoder.yudao.module.commons.utils.convert.CollConvertUtils;
import com.iocoder.yudao.module.system.domain.*;
import com.iocoder.yudao.module.system.mapper.UserMapper;
import com.iocoder.yudao.module.system.service.*;
import com.iocoder.yudao.module.system.vo.user.*;
import com.iocoder.yudao.module.system.vo.user.profile.UserProfileUpdatePasswordReqVO;
import com.iocoder.yudao.module.system.vo.user.profile.UserProfileUpdateReqVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.iocoder.yudao.module.commons.constant.ErrorCodeConstants.REQ_ARGS_NOT_NULL;
import static com.iocoder.yudao.module.commons.constant.ErrorCodeConstants.UserErrorCode.*;

/**
 * <p>
 * ??????????????? ???????????????
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
    UserRoleService userRoleService;

    @Resource
    UserService userService;

    @Resource
    NameCodeService nameCodeService;

    @Value("${default.password}")
    private String password;

    @Override
    public PageResult<UserDO> selectUserList(UserPageQueryRequestVo requestVo) {
        Page<UserDO> page = new Page<>(requestVo.getPageNo(), requestVo.getPageSize());
        List<UserDO> userDOList = baseMapper.selectUserList(requestVo, deptService.getDeptCondition(requestVo.getDeptId()));
        if (CollectionUtils.isNotEmpty(userDOList)) {
            userDOList = userDOList.stream().filter(ArrayUtils.distinctByKey(UserDO::getId)).collect(Collectors.toList());
        }
        page.setRecords(userDOList);
        page.setTotal(userDOList.size());
        return new PageResult<>(page.getRecords(), page.getTotal());
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
        // ????????????
        checkCreateOrUpdate(null, reqVO.getUsername(), reqVO.getMobile(), reqVO.getEmail(), reqVO.getDeptIds(),
                reqVO.getPostIds());
        // ????????????????????????
        UserDO userDO = new UserDO();
        BeanUtil.copyProperties(reqVO, userDO);
        userDO.setStatus(CommonStatusEnum.ENABLE.getStatus());
        userDO.setPassword(passwordEncoder.encode(password));
        baseMapper.insert(userDO);
        // ??????????????????
        if (CollectionUtils.isNotEmpty(reqVO.getDeptIds())) {
            reqVO.getDeptIds().forEach(deptId -> {
                userDeptService.save(new UserDeptDO().setUserId(userDO.getId()).setDeptId(deptId));
            });
        }
        // ??????????????????
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
        // ????????????
        checkCreateOrUpdate(reqVO.getId(), reqVO.getUsername(), reqVO.getMobile(), reqVO.getEmail(), reqVO.getDeptIds(),
                reqVO.getPostIds());

        // ????????????????????????
        UserDO userDO = new UserDO();
        BeanUtil.copyProperties(reqVO, userDO);
        baseMapper.updateById(userDO);
        // ????????????????????????
        updateUserDeptInfo(reqVO);
        // ????????????????????????
        updateUserPostInfo(reqVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(Long userId) {
        // ??????????????????
        checkUserExist(userId);
        // ????????????????????????
        userService.removeById(userId);
        // ????????????????????????
        userDeptService.remove(new LambdaUpdateWrapper<UserDeptDO>()
                .in(UserDeptDO::getUserId, userId)
        );
        // ????????????????????????
        userPostService.remove(new LambdaUpdateWrapper<UserPostDO>()
                .in(UserPostDO::getUserId, userId)
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUserBatch(UserBatchDeleteReqVO batchDeleteReqVO) {
        if (CollectionUtils.isEmpty(batchDeleteReqVO.getUserIds())) {
            throw ServiceExceptionUtil.exception(REQ_ARGS_NOT_NULL);
        }
        batchDeleteReqVO.getUserIds().forEach(this::deleteUser);
    }

    @Override
    public void updateUserPassword(Long userId, String password) {
        // ??????????????????
        checkUserExist(userId);
        // ????????????
        UserDO updateObj = new UserDO();
        updateObj.setId(userId);
        updateObj.setPassword(passwordEncoder.encode(password));
        userService.updateById(updateObj);
    }

    @Override
    public void updateUserPassword(Long id, UserProfileUpdatePasswordReqVO reqVO) {
        // ?????????????????????
        checkOldPassword(id, reqVO.getOldPassword());
        // ????????????????????????????????????
        UserDO updateObj = new UserDO();
        updateObj.setId(id);
        updateObj.setPassword(passwordEncoder.encode(reqVO.getNewPassword()));
        userService.updateById(updateObj);
    }

    @Override
    public void updateUserStatus(Long userId, Integer status) {
        // ??????????????????
        checkUserExist(userId);
        // ????????????
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
        // ??????????????????
        checkUserExist(userId);
        // ????????????????????????
        UserDO userDO = baseMapper.selectById(userId);
        if (ObjectUtils.isEmpty(userDO)) {
            return null;
        }
        UserRespVO userRespVO = new UserRespVO();
        BeanUtil.copyProperties(userDO, userRespVO);
        // ????????????????????????
        List<DeptDO> userDeptInfoList = userDeptService.selectDeptInfoByUserId(userId);
        if (CollectionUtils.isNotEmpty(userDeptInfoList)) {
            userRespVO.setDeptList(userDeptInfoList);
        }
        // ????????????????????????
        List<PostDO> userPostInfoList = userPostService.selectPostInfoByUserId(userId);
        if (CollectionUtils.isNotEmpty(userPostInfoList)) {
            userRespVO.setPostList(userPostInfoList);
        }
//        // ????????????????????????
//        List<RoleDO> userRoleInfoList = userRoleService.selectRoleInfoByUserId(userId);
//        if (CollectionUtils.isNotEmpty(userRoleInfoList)) {
//            userRespVO.setRoleList(userRoleInfoList);
//        }
        return userRespVO;
    }

    @Override
    public void updateUserProfile(Long loginUserId, UserProfileUpdateReqVO reqVO) {
        // ??????????????????
        checkUserExist(loginUserId);
        // ?????????????????????
        checkMobileUnique(loginUserId, reqVO.getMobile());
        // ??????????????????
        checkEmailUnique(loginUserId, reqVO.getEmail());
        UserDO userDO = new UserDO();
        BeanUtil.copyProperties(reqVO, userDO);
        userDO.setId(loginUserId);
        // ?????????????????????????????????
        baseMapper.updateById(userDO);
    }

    @Override
    public boolean updateUserAvatar(Long loginUserId, String avatar) {
        // ??????????????????
        checkUserExist(loginUserId);
        // ????????????
        UserDO updateObj = new UserDO();
        updateObj.setId(loginUserId);
        updateObj.setAvatar(avatar);
        return baseMapper.updateById(updateObj) > 0;
    }

    @Override
    public List<UserDO> selectUserByNickName(String userNickname) {
        return baseMapper.selectList(new LambdaQueryWrapperX<UserDO>()
                .likeIfPresent(UserDO::getUsername, userNickname)
                .orIfPresent()
                .likeIfPresent(UserDO::getNickname, userNickname)
        );
    }

    @Override
    public UserRoleRespVO getUserRoleList(Long userId) {
        List<RoleDO> userRoleList = userRoleService.selectRoleInfoByUserId(userId);
        return UserRoleRespVO.builder().userId(userId).userRoleList(userRoleList).build();
    }

    @Override
    public String[] getUserNamePinyin(String username) {
        Assertion.isBlank(username, "?????????????????????");
        return ChineseCharConvertUtil.chineseConversionPinyin(username);
    }

    @Override
    public UserNameSplitRespVO getUserLastNameAndFirstName(String username) {
        Assertion.isBlank(username, "?????????????????????");
        List<String> nameSplitList = NameSplitUtils.nameSplit(username);
        if (CollectionUtils.isEmpty(nameSplitList)) {
            return null;
        }
        return UserNameSplitRespVO.builder().lastName(nameSplitList.get(0)).firstName(nameSplitList.get(1)).build();
    }

    @Override
    public String getUserNameElectronicCode(String username) {
        String nameElectronicCode = StringUtils.EMPTY;
        // ??????????????????
        String[] usernames = username.split("");
        for (String character : usernames) {
            // ????????????
            String code = nameCodeService.getCodeByName(character);
            if (StringUtils.isNotEmpty(code)) {
                nameElectronicCode = nameElectronicCode.concat(code).concat(" ");
            } else {
                // ??????????????????????????????????????????
                return StringUtils.EMPTY;
            }
        }
        return nameElectronicCode.trim();
    }


    /**
     * ????????????????????????
     *
     * @param reqVO ????????????
     */
    private void updateUserDeptInfo(UserUpdateReqVO reqVO) {

        Set<Long> dbDeptIds = CollConvertUtils.convertSet(userDeptService.selectUserDeptListByUserId(reqVO.getId()),
                UserDeptDO::getDeptId);
        // ????????????????????????????????????
        Set<Long> deptIds = reqVO.getDeptIds().stream().filter(Objects::nonNull).collect(Collectors.toSet());
        Collection<Long> createDeptIds = CollUtil.subtract(deptIds, dbDeptIds);
        Collection<Long> deleteDeptIds = CollUtil.subtract(dbDeptIds, deptIds);
        // ?????????????????????
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
     * ????????????????????????
     *
     * @param reqVO ????????????
     */
    private void updateUserPostInfo(UserUpdateReqVO reqVO) {
        Set<Long> dbPostIds = CollConvertUtils.convertSet(userPostService.selectUserPostListByUserId(reqVO.getId()),
                UserPostDO::getPostId);
        // ????????????????????????????????????
        Set<Long> postIds = reqVO.getPostIds().stream().filter(Objects::nonNull).collect(Collectors.toSet());
        Collection<Long> createPostIds = CollUtil.subtract(postIds, dbPostIds);
        Collection<Long> deletePostIds = CollUtil.subtract(dbPostIds, postIds);
        // ?????????????????????
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
                                     @Email(message = "?????????????????????") @Size(max = 50, message = "???????????????????????? 50 ?????????") String email,
                                     List<Long> deptIds, List<Long> postIds
    ) {
        // ????????????????????????
        checkUserExist(id);
        // ???????????????????????????
        checkUsernameUnique(id, username);
        // ?????????????????????
        checkMobileUnique(id, mobile);
        // ??????????????????
        checkEmailUnique(id, email);
        // ????????????????????????
        deptService.validDepts(deptIds);
        // ????????????????????????
        postService.validPosts(postIds);
    }

    /**
     * ????????????????????????
     *
     * @param id ????????????
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
     * ???????????????????????????
     *
     * @param id       ????????????
     * @param username ?????????
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
     * ???????????????????????????
     *
     * @param id     ????????????
     * @param mobile ?????????
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
     * ????????????????????????
     *
     * @param id    ????????????
     * @param email ??????
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
     * ???????????????
     *
     * @param id          ?????? id
     * @param oldPassword ?????????
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
