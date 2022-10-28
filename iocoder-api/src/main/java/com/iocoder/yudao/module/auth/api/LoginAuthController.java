package com.iocoder.yudao.module.auth.api;

import cn.hutool.core.util.StrUtil;
import com.iocoder.yudao.module.commons.core.domain.CommonResult;
import com.iocoder.yudao.module.commons.core.domain.LoginUser;
import com.iocoder.yudao.module.commons.enums.common.CommonStatusEnum;
import com.iocoder.yudao.module.commons.enums.login.LoginLogTypeEnum;
import com.iocoder.yudao.module.commons.enums.menu.MenuTypeEnum;
import com.iocoder.yudao.module.commons.utils.SecurityUtils;
import com.iocoder.yudao.module.framework.config.security.web.service.JwtTokenService;
import com.iocoder.yudao.module.framework.config.web.auth.service.LoginAuthService;
import com.iocoder.yudao.module.system.domain.MenuDO;
import com.iocoder.yudao.module.system.service.PermissionService;
import com.iocoder.yudao.module.system.vo.UserInfoReqsVo;
import com.iocoder.yudao.module.system.vo.auth.AuthLoginReqVO;
import com.iocoder.yudao.module.system.vo.auth.AuthLoginRespVO;
import com.iocoder.yudao.module.system.vo.auth.AuthMenuRespVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.iocoder.yudao.module.commons.core.domain.CommonResult.success;
import static java.util.Collections.singleton;

/**
 * @author wu kai
 * @date 2022/6/23
 */
@Api(tags = "管理后台 - 用户登录")
@RestController
@Validated
public class LoginAuthController {

    @Resource
    LoginAuthService loginAuthService;
    @Resource
    PermissionService permissionService;
    @Resource
    JwtTokenService tokenService;

//    @Encrypt
    @PostMapping("/login")
    @ApiOperation("使用账号密码登录")
    public CommonResult<AuthLoginRespVO> login(@RequestBody @Valid AuthLoginReqVO reqVO) {
        return success(loginAuthService.login(reqVO));
    }

    @PostMapping("/auth/logout")
    @PermitAll
    @ApiOperation("登出系统")
    public CommonResult<Boolean> logout(HttpServletRequest request) throws Exception {
        String token = tokenService.getToken(request);
        if (StrUtil.isNotBlank(token)) {
            loginAuthService.logout(token,request, LoginLogTypeEnum.LOGOUT_SELF.getType());
        }
        return success(true);
    }

    @GetMapping("/getInfo")
    @ApiOperation("获取登录用户信息")
    public CommonResult<UserInfoReqsVo> getLoginUserInfo() {
        // 获取登录用户基本信息
        LoginUser loginUser = SecurityUtils.getLoginUser();
        // 获取登录用户角色编码集合
        Set<String> roleCode = permissionService.getRolePermission(loginUser.getUser());
        // 菜单权限集合
        Set<String> permissions = permissionService.getMenuPermission(loginUser);
        return success(UserInfoReqsVo.builder().user(loginUser.getUser()).role(roleCode).permissions(permissions).build());
    }

    @GetMapping("/user-menus-list")
    @ApiOperation("获取登录用户菜单权限")
    public CommonResult<List<AuthMenuRespVO>> getLoginUserMenu() {
        // 获取用户角色编号集合（角色状态为已开启的角色）
        Set<Long> roleIds = permissionService.getUserRoleIds(SecurityUtils.getLoginUser(), singleton(CommonStatusEnum.ENABLE.getStatus()));
        // 获取用户菜单集合
        Set<Integer> menuType = new HashSet<>();
        menuType.add(MenuTypeEnum.DIR.getType());
        menuType.add(MenuTypeEnum.MENU.getType());
        // 获取用户拥有的、并已开启的菜单列表
        List<MenuDO> menuList = permissionService.getUserMenusList(roleIds, menuType, singleton(CommonStatusEnum.ENABLE.getStatus()));
        // 转换成 Tree 结构返回
        return success(permissionService.buildMenuTree(menuList));
    }

}
