package com.iocoder.yudao.module.system.api.user;

import com.iocoder.yudao.module.commons.core.domain.*;
import com.iocoder.yudao.module.commons.exception.ServiceExceptionUtil;
import com.iocoder.yudao.module.commons.utils.BeanUtil;
import com.iocoder.yudao.module.commons.utils.convert.CollConvertUtils;
import com.iocoder.yudao.module.system.domain.DeptDO;
import com.iocoder.yudao.module.system.domain.PostDO;
import com.iocoder.yudao.module.system.service.DeptService;
import com.iocoder.yudao.module.system.service.PostService;
import com.iocoder.yudao.module.system.service.UserService;
import com.iocoder.yudao.module.system.vo.user.profile.UserProfileRespVO;
import com.iocoder.yudao.module.system.vo.user.profile.UserProfileUpdatePasswordReqVO;
import com.iocoder.yudao.module.system.vo.user.profile.UserProfileUpdateReqVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;

import static com.iocoder.yudao.module.commons.constant.ErrorCodeConstants.FileErrorCode.FILE_IS_EMPTY;
import static com.iocoder.yudao.module.commons.core.domain.CommonResult.success;
import static com.iocoder.yudao.module.commons.utils.SecurityUtils.getLoginUser;
import static com.iocoder.yudao.module.commons.utils.SecurityUtils.getLoginUserId;

/**
 * @Author: kai wu
 * @Date: 2022/6/26 14:27
 */
@Api(tags = "管理后台 - 用户个人中心")
@RestController
@RequestMapping("/system/user/profile")
@Validated
@Slf4j
public class UserProfileController {

    @Resource
    DeptService deptService;

    @Resource
    PostService postService;

    @Resource
    UserService userService;

    @GetMapping("/getUserProfile")
    @ApiOperation("获得登录用户信息")
    public CommonResult<UserProfileRespVO> getUserProfile() {
        // 获得登陆用户基本信息
        LoginUser loginUser = getLoginUser();
        UserDO user = loginUser.getUser();
        UserProfileRespVO profileRespVO = new UserProfileRespVO();
        BeanUtil.copyProperties(user, profileRespVO);
        // 获取登陆用户部门信息列表
        if (CollectionUtils.isNotEmpty(loginUser.getDeptVoList())) {
            Set<Long> deptIds = CollConvertUtils.convertSet(loginUser.getDeptVoList(), DeptVo::getId);
            List<DeptDO> deptInfoList = deptService.getSimpleDepts(deptIds);
            profileRespVO.setDeptList(deptInfoList);
        }
        // 获取登陆用户岗位信息列表
        if (CollectionUtils.isNotEmpty(loginUser.getPostVoList())) {
            Set<Long> postIds = CollConvertUtils.convertSet(loginUser.getPostVoList(), PostVo::getId);
            List<PostDO> postInfoList = postService.getSimplePostInfos(postIds);
            profileRespVO.setPostList(postInfoList);
        }
        return success(profileRespVO);
    }

    @PutMapping("/update")
    @ApiOperation("修改用户个人信息")
    public CommonResult<Boolean> updateUserProfile(@Valid @RequestBody UserProfileUpdateReqVO reqVO) {
        userService.updateUserProfile(getLoginUserId(), reqVO);
        return success(true);
    }

    @PutMapping("/update-password")
    @ApiOperation("修改用户个人密码")
    public CommonResult<Boolean> updateUserProfilePassword(@Valid @RequestBody UserProfileUpdatePasswordReqVO reqVO) {
        userService.updateUserPassword(getLoginUserId(), reqVO);
        return success(true);
    }

    @PutMapping("/update-avatar")
    @ApiOperation("上传用户个人头像")
    public CommonResult<String> updateUserAvatar(@RequestParam("avatarFile") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw ServiceExceptionUtil.exception(FILE_IS_EMPTY);
        }
        String avatar = userService.updateUserAvatar(getLoginUserId(), file.getInputStream());
        return success(avatar);
    }
}
