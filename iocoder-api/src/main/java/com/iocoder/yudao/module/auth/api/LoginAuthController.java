package com.iocoder.yudao.module.auth.api;

import com.iocoder.yudao.module.commons.core.domain.CommonResult;
import com.iocoder.yudao.module.commons.core.domain.LoginUser;
import com.iocoder.yudao.module.commons.enums.common.CommonStatusEnum;
import com.iocoder.yudao.module.commons.enums.menu.MenuTypeEnum;
import com.iocoder.yudao.module.commons.utils.SecurityUtils;
import com.iocoder.yudao.module.framework.config.web.auth.service.LoginAuthService;
import com.iocoder.yudao.module.framework.config.web.auth.vo.AuthLoginReqVO;
import com.iocoder.yudao.module.framework.config.web.auth.vo.AuthLoginRespVO;
import com.iocoder.yudao.module.framework.config.web.auth.vo.AuthMenuRespVO;
import com.iocoder.yudao.module.system.domain.MenuDO;
import com.iocoder.yudao.module.system.service.MenuService;
import com.iocoder.yudao.module.system.service.PermissionService;
import com.iocoder.yudao.module.system.vo.UserInfoReqsVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
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
    MenuService menuService;

    @PostMapping("/login")
    @ApiOperation("使用账号密码登录")
    public CommonResult<AuthLoginRespVO> login(@RequestBody @Valid AuthLoginReqVO reqVO) {
        return success(loginAuthService.login(reqVO));
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
    public CommonResult<List<AuthMenuRespVO>> getLoginUserMenu() {
        // 获取用户角色编号集合（角色状态为已开启的角色）
        Set<Long> roleIds = permissionService.getUserRoleIds(SecurityUtils.getLoginUser(), singleton(CommonStatusEnum.ENABLE.getStatus()));
        // 获取用户菜单集合
        Set<Integer> menuType = new HashSet<>();
        menuType.add(MenuTypeEnum.DIR.getType());
        menuType.add(MenuTypeEnum.MENU.getType());
        List<MenuDO> menuList = permissionService.getUserMenusList(roleIds, menuType, singleton(CommonStatusEnum.ENABLE.getStatus()));
        return success(new ArrayList<AuthMenuRespVO>());
    }

}
