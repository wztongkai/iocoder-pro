package com.iocoder.yudao.module.framework.config.web.auth.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.iocoder.yudao.module.commons.constant.Constants;
import com.iocoder.yudao.module.commons.core.LambdaQueryWrapperX;
import com.iocoder.yudao.module.commons.core.domain.*;
import com.iocoder.yudao.module.commons.core.redis.RedisCache;
import com.iocoder.yudao.module.commons.enums.common.CommonStatusEnum;
import com.iocoder.yudao.module.commons.enums.login.LoginLogTypeEnum;
import com.iocoder.yudao.module.commons.enums.login.LoginResultEnum;
import com.iocoder.yudao.module.commons.enums.user.UserTypeEnum;
import com.iocoder.yudao.module.commons.exception.ServiceException;
import com.iocoder.yudao.module.commons.exception.ServiceExceptionUtil;
import com.iocoder.yudao.module.commons.exception.UserPasswordNotMatchException;
import com.iocoder.yudao.module.commons.utils.BeanUtil;
import com.iocoder.yudao.module.commons.utils.SecurityUtils;
import com.iocoder.yudao.module.commons.utils.ServletUtils;
import com.iocoder.yudao.module.commons.utils.monitor.TracerUtils;
import com.iocoder.yudao.module.commons.utils.validation.ValidationUtils;
import com.iocoder.yudao.module.framework.config.security.web.service.JwtTokenService;
import com.iocoder.yudao.module.framework.config.web.auth.dto.LoginLogCreateReqDTO;
import com.iocoder.yudao.module.framework.config.web.auth.service.CaptchaService;
import com.iocoder.yudao.module.framework.config.web.auth.service.LoginAuthService;
import com.iocoder.yudao.module.framework.config.web.auth.service.LoginLogService;
import com.iocoder.yudao.module.system.vo.auth.AuthLoginReqVO;
import com.iocoder.yudao.module.system.vo.auth.AuthLoginRespVO;
import com.iocoder.yudao.module.system.domain.DeptDO;
import com.iocoder.yudao.module.system.domain.PostDO;
import com.iocoder.yudao.module.system.domain.RoleDO;
import com.iocoder.yudao.module.system.service.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Validator;
import java.util.*;

import static com.iocoder.yudao.module.commons.constant.ErrorCodeConstants.AuthCode.*;
import static com.iocoder.yudao.module.commons.enums.role.RoleCodeEnum.SUPER_ADMIN;

/**
 * ??????????????????
 *
 * @author wu kai
 * @since 2022/6/24
 */
@Service
public class LoginAuthServiceImpl implements LoginAuthService {

    @Resource
    CaptchaService captchaService;

    @Resource
    Validator validator;

    @Resource
    RedisCache redisCache;

    @Resource
    LoginLogService loginLogService;

    @Resource
    UserService userService;

    @Resource
    UserPostService userPostService;

    @Resource
    UserDeptService userDeptService;

    @Resource
    UserRoleService userRoleService;

    @Resource
    AuthenticationManager authenticationManager;

    @Resource
    MenuService menuService;

    @Resource
    JwtTokenService jwtTokenService;

    @Override
    public AuthLoginRespVO login(AuthLoginReqVO reqVO) {
        // ???????????????
        verifyCaptcha(reqVO);
        // ??????
        UserDO userDO = login(reqVO.getUsername(), reqVO.getPassword());
        // ???????????? ??????token
        return createTokenAfterLoginSuccess(userDO.getId(), reqVO.getUsername(), reqVO.getPassword());
    }

    @Override
    public void logout(String token, HttpServletRequest request, Long type) throws Exception {
        // ??????token
        LoginUser loginUser = jwtTokenService.getLoginUser(request);
        jwtTokenService.delLoginUser(token);
        // ??????????????????
        if(Objects.nonNull(loginUser)){
            createLogoutLog(loginUser.getUserId(), UserTypeEnum.MANAGE.getValue(), type);
        }

    }

    private void createLogoutLog(Long userId, Integer userType, Long logType) {
        LoginLogCreateReqDTO reqDTO = new LoginLogCreateReqDTO();
        reqDTO.setLogType(logType);
        reqDTO.setTraceId(TracerUtils.getTraceId());
        reqDTO.setUserId(userId);
        reqDTO.setUserType(userType);
        reqDTO.setUsername(getUsername(userId));
        reqDTO.setUserAgent(ServletUtils.getUserAgent());
        reqDTO.setUserIp(ServletUtils.getClientIP());
        reqDTO.setResult(LoginResultEnum.SUCCESS.getResult());
        loginLogService.createLoginLog(reqDTO);
    }

    /**
     * ???????????????
     *
     * @param reqVO ????????????
     */
    private void verifyCaptcha(AuthLoginReqVO reqVO) {
        // ?????????????????????????????????????????????????????????
        if (!captchaService.isCaptchaEnable()) {
            return;
        }
        //???????????????
        ValidationUtils.validate(validator, reqVO, AuthLoginReqVO.CodeEnableGroup.class);
        // ??????????????????
        final LoginLogTypeEnum logTypeEnum = LoginLogTypeEnum.LOGIN_USERNAME;
        // ?????????????????? redis key
        String verifyKey = Constants.CAPTCHA_CODE_KEY + reqVO.getUuid();
        // ??????uuid??????????????????
        String captchaCode = redisCache.getCacheObject(verifyKey);
        // ??????????????????
        if (StringUtils.isBlank(captchaCode)) {
            createLoginLog(null, reqVO.getUsername(), logTypeEnum, LoginResultEnum.CAPTCHA_NOT_FOUND);
            throw ServiceExceptionUtil.exception(AUTH_LOGIN_CAPTCHA_NOT_FOUND);
        }
        // ??????????????????
        if (!captchaCode.equals(reqVO.getCode())) {
            createLoginLog(null, reqVO.getUsername(), logTypeEnum, LoginResultEnum.CAPTCHA_CODE_ERROR);
            throw ServiceExceptionUtil.exception(AUTH_LOGIN_CAPTCHA_CODE_ERROR);
        }
        // ??????????????????????????????redis?????????????????????
        captchaService.deleteCaptchaCode(reqVO.getUuid());
    }

    /**
     * ??????
     *
     * @param username ?????????
     * @param password ??????
     * @return ????????????
     */
    private UserDO login(String username, String password) {
        final LoginLogTypeEnum logTypeEnum = LoginLogTypeEnum.LOGIN_USERNAME;
        UserDO user = userService.getOne(new LambdaQueryWrapperX<UserDO>()
                .eq(UserDO::getUsername, username)
                .orderByDesc(UserDO::getCreateTime).last("limit 1")
        );
        // ????????????
        if (Objects.isNull(user)) {
            createLoginLog(null, username, logTypeEnum, LoginResultEnum.BAD_CREDENTIALS);
            throw ServiceExceptionUtil.exception(AUTH_LOGIN_BAD_CREDENTIALS);
        }
        // ????????????????????????
        if (!SecurityUtils.matchesPassword(password, user.getPassword())) {
            createLoginLog(user.getId(), username, logTypeEnum, LoginResultEnum.BAD_CREDENTIALS);
            throw ServiceExceptionUtil.exception(AUTH_LOGIN_BAD_CREDENTIALS);
        }
        // ??????????????????
        if (ObjectUtil.notEqual(user.getStatus(), CommonStatusEnum.ENABLE.getStatus())) {
            createLoginLog(user.getId(), username, logTypeEnum, LoginResultEnum.USER_DISABLED);
            throw ServiceExceptionUtil.exception(AUTH_LOGIN_USER_DISABLED);
        }
        return user;

    }

    /**
     * ????????????????????? token
     *
     * @param userId   ????????????
     * @param username ?????????
     * @param password ??????
     * @return token??????
     */
    private AuthLoginRespVO createTokenAfterLoginSuccess(Long userId, String username, String password) {
        // ????????????
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                // ?????????????????? -- ?????????????????????
                this.createLoginLog(userId, username, LoginLogTypeEnum.LOGIN_USERNAME, LoginResultEnum.BAD_CREDENTIALS);
                throw new UserPasswordNotMatchException();
            } else {
                // ??????????????????
                this.createLoginLog(userId, username, LoginLogTypeEnum.LOGIN_USERNAME, LoginResultEnum.UNKNOWN_ERROR);
                throw new ServiceException(500, e.getMessage());
            }
        }
        // ??????????????????
        this.createLoginLog(userId, username, LoginLogTypeEnum.LOGIN_USERNAME, LoginResultEnum.SUCCESS);
        // ????????????????????????
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();

        // ????????????????????????
        List<DeptDO> userDeptList = userDeptService.selectDeptInfoByUserId(loginUser.getUserId());
        List<DeptVo> deptInfoList = new ArrayList<>();
        BeanUtil.copyListProperties(userDeptList, deptInfoList, DeptVo.class);
        loginUser.setDeptVoList(deptInfoList);

        // ????????????????????????
        List<PostDO> userPostList = userPostService.selectPostInfoByUserId(loginUser.getUserId());
        List<PostVo> postInfoList = new ArrayList<>();
        BeanUtil.copyListProperties(userPostList, postInfoList, PostVo.class);
        loginUser.setPostVoList(postInfoList);

        // ????????????????????????
        List<RoleDO> userRoleList = userRoleService.selectRoleInfoByUserId(loginUser.getUserId());
        List<RoleVO> roleInfoList = new ArrayList<>();
        BeanUtil.copyListProperties(userRoleList, roleInfoList, RoleVO.class);
        loginUser.setRoleVoList(roleInfoList);

        // ????????????????????????
        // ?????????????????????????????????????????????
        List<String> permissions = new ArrayList<>();
        boolean flag = userRoleList.stream().anyMatch(roleDO -> roleDO.getCode().equals(SUPER_ADMIN.getCode()));
        if (flag) {
            permissions.add("*:*:*");
        } else {
            permissions.addAll(menuService.selectMenuPermByUserId(loginUser.getUserId()));
        }
        loginUser.setPermissions(permissions);

        // ??????token
        String token = jwtTokenService.createToken(loginUser);
        // ??????????????????
        return AuthLoginRespVO.builder().userId(userId).accessToken(token).refreshToken(IdUtil.fastSimpleUUID()).build();
    }

    /**
     * ??????????????????
     *
     * @param userId      ????????????
     * @param username    ????????????
     * @param logTypeEnum ??????????????????
     * @param loginResult ??????????????????
     */
    private void createLoginLog(Long userId, String username,
                                LoginLogTypeEnum logTypeEnum, LoginResultEnum loginResult) {
        // ??????????????????
        LoginLogCreateReqDTO reqDTO = new LoginLogCreateReqDTO();
        reqDTO.setLogType(logTypeEnum.getType());
        reqDTO.setTraceId(TracerUtils.getTraceId());
        reqDTO.setUserId(userId);
        reqDTO.setUserType(UserTypeEnum.MANAGE.getValue());
        reqDTO.setUsername(username);
        reqDTO.setUserAgent(ServletUtils.getUserAgent());
        reqDTO.setUserIp(ServletUtils.getClientIP());
        reqDTO.setResult(loginResult.getResult());
        loginLogService.createLoginLog(reqDTO);
        // ????????????????????????
        if (userId != null && Objects.equals(LoginResultEnum.SUCCESS.getResult(), loginResult.getResult())) {
            userService.updateUserLogin(userId, ServletUtils.getClientIP());
        }
    }

    private String getUsername(Long userId) {
        if (userId == null) {
            return null;
        }
        UserDO user = userService.getById(userId);
        return user != null ? user.getUsername() : null;
    }
}
