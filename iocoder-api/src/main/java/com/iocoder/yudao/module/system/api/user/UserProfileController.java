package com.iocoder.yudao.module.system.api.user;

import com.iocoder.yudao.module.commons.annotation.Log;
import com.iocoder.yudao.module.commons.config.iocoderConfig.IocoderConfig;
import com.iocoder.yudao.module.commons.core.domain.*;
import com.iocoder.yudao.module.commons.enums.BusinessType;
import com.iocoder.yudao.module.commons.exception.ServiceExceptionUtil;
import com.iocoder.yudao.module.commons.utils.BeanUtil;
import com.iocoder.yudao.module.commons.utils.convert.CollConvertUtils;
import com.iocoder.yudao.module.commons.utils.file.FileUploadUtils;
import com.iocoder.yudao.module.commons.utils.file.MimeTypeUtils;
import com.iocoder.yudao.module.framework.config.security.web.service.JwtTokenService;
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

import static com.iocoder.yudao.module.commons.constant.ErrorCodeConstants.FileErrorCode.*;
import static com.iocoder.yudao.module.commons.core.domain.CommonResult.error;
import static com.iocoder.yudao.module.commons.core.domain.CommonResult.success;
import static com.iocoder.yudao.module.commons.utils.SecurityUtils.getLoginUser;
import static com.iocoder.yudao.module.commons.utils.SecurityUtils.getLoginUserId;

/**
 * ??????????????????????????????
 *
 * @Author: kai wu
 * @Date: 2022/6/26 14:27
 */
@Api(tags = "???????????? - ??????????????????")
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

    @Resource
    JwtTokenService jwtTokenService;

    @Resource
    IocoderConfig iocoderConfig;

    @GetMapping("/getUserProfile")
    @ApiOperation("????????????????????????")
    public CommonResult<UserProfileRespVO> getUserProfile() {
        // ??????????????????????????????
        LoginUser loginUser = getLoginUser();
        UserDO user = loginUser.getUser();
        UserProfileRespVO profileRespVO = new UserProfileRespVO();
        BeanUtil.copyProperties(user, profileRespVO);
        // ????????????????????????????????????
        if (CollectionUtils.isNotEmpty(loginUser.getDeptVoList())) {
            Set<Long> deptIds = CollConvertUtils.convertSet(loginUser.getDeptVoList(), DeptVo::getId);
            List<DeptDO> deptInfoList = deptService.getSimpleDepts(deptIds);
            profileRespVO.setDeptList(deptInfoList);
        }
        // ????????????????????????????????????
        if (CollectionUtils.isNotEmpty(loginUser.getPostVoList())) {
            Set<Long> postIds = CollConvertUtils.convertSet(loginUser.getPostVoList(), PostVo::getId);
            List<PostDO> postInfoList = postService.getSimplePostInfos(postIds);
            profileRespVO.setPostList(postInfoList);
        }
        return success(profileRespVO);
    }

    @Log(title = "????????????",businessType = BusinessType.UPDATE)
    @PutMapping("/update")
    @ApiOperation("????????????????????????")
    public CommonResult<Boolean> updateUserProfile(@Valid @RequestBody UserProfileUpdateReqVO reqVO) {
        userService.updateUserProfile(getLoginUserId(), reqVO);
        return success(true);
    }

    @Log(title = "????????????",businessType = BusinessType.UPDATE)
    @PutMapping("/update-password")
    @ApiOperation("????????????????????????")
    public CommonResult<Boolean> updateUserProfilePassword(@Valid @RequestBody UserProfileUpdatePasswordReqVO reqVO) {
        userService.updateUserPassword(getLoginUserId(), reqVO);
        return success(true);
    }

    @Log(title = "????????????",businessType = BusinessType.UPLOAD)
    @PutMapping("/update-avatar")
    @ApiOperation("????????????????????????")
    public CommonResult<String> updateUserAvatar(@RequestParam("avatarFile") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw ServiceExceptionUtil.exception(FILE_IS_EMPTY);
        }
        String avatar = FileUploadUtils.upload(iocoderConfig.getProfile()+"/avatar", file, MimeTypeUtils.IMAGE_EXTENSION);
        LoginUser loginUser = getLoginUser();
        if (userService.updateUserAvatar(loginUser.getUserId(), avatar)) {
            // ????????????????????????
            loginUser.getUser().setAvatar(avatar);
            jwtTokenService.setLoginUser(loginUser);
            return success(avatar);
        }
        return error(FILE_UPDATE_EXCEPTION);
    }
}
