package com.iocoder.yudao.module.system.api.user;


import com.iocoder.yudao.module.commons.core.domain.CommonResult;
import com.iocoder.yudao.module.commons.core.domain.PageResult;
import com.iocoder.yudao.module.commons.core.domain.UserDO;
import com.iocoder.yudao.module.commons.enums.CommonStatusEnum;
import com.iocoder.yudao.module.commons.utils.BeanUtil;
import com.iocoder.yudao.module.system.domain.DeptDO;
import com.iocoder.yudao.module.system.domain.PostDO;
import com.iocoder.yudao.module.system.service.UserDeptService;
import com.iocoder.yudao.module.system.service.UserPostService;
import com.iocoder.yudao.module.system.service.UserService;
import com.iocoder.yudao.module.system.vo.user.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static com.iocoder.yudao.module.commons.core.domain.CommonResult.success;

/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 *
 * @author wu kai
 * @since 2022-06-22
 */
@Api(tags = "管理后台 - 用户")
@RestController
@RequestMapping("/system/user")
@Validated
public class UserController {

    @Resource
    UserService userService;

    @Resource
    UserPostService userPostService;

    @Resource
    UserDeptService userDeptService;


    @GetMapping("/getUserPage")
    @ApiOperation("获得用户分页列表")
    public CommonResult<PageResult<UserPageItemRespVO>> getUserPage(@Valid UserPageQueryRequestVo requestVo) {
        // 获得用户分页列表
        PageResult<UserDO> pageResult = userService.selectUserList(requestVo);
        if (CollectionUtils.isEmpty(pageResult.getList())) {
            return success(new PageResult<>(pageResult.getTotal()));
        }
        // 获取用户其他信息
        List<UserPageItemRespVO> userList = new ArrayList<>(pageResult.getList().size());
        pageResult.getList().forEach(userInfo -> {
            UserPageItemRespVO userPageItemRespVO = new UserPageItemRespVO();
            BeanUtil.copyProperties(userInfo, userPageItemRespVO);
            // 获取用户所有部门信息
            List<DeptDO> userDeptList = userDeptService.selectDeptInfoByUserId(userInfo.getId());
            userPageItemRespVO.setDeptList(userDeptList);
            // 获取用户所有岗位信息
            List<PostDO> userPostList = userPostService.selectPostInfoByUserId(userInfo.getId());
            userPageItemRespVO.setPostList(userPostList);
            userList.add(userPageItemRespVO);
        });
        return success(new PageResult<>(userList, pageResult.getTotal()));
    }

    @PostMapping("/create")
    @ApiOperation("新增用户")
    public CommonResult<Long> createUser(@Valid @RequestBody UserCreateReqVO reqVO) {
        return success(userService.createUser(reqVO));
    }

    @PutMapping("update")
    @ApiOperation("修改用户")
    public CommonResult<Boolean> updateUser(@Valid @RequestBody UserUpdateReqVO reqVO) {
        userService.updateUser(reqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除用户")
    @ApiImplicitParam(name = "id", value = "用户", required = true, example = "1540614322441457665", dataTypeClass = Long.class)
    public CommonResult<Boolean> deleteUser(@RequestParam("id") Long id) {
        userService.deleteUser(id);
        return success(true);
    }

    @PutMapping("/update-password")
    @ApiOperation("重置用户密码")
    public CommonResult<Boolean> updateUserPassword(@Valid @RequestBody UserUpdatePasswordReqVO reqVO) {
        userService.updateUserPassword(reqVO.getId(), reqVO.getPassword());
        return success(true);
    }

    @PutMapping("/update-status")
    @ApiOperation("修改用户状态")
    public CommonResult<Boolean> updateUserStatus(@Valid @RequestBody UserUpdateStatusReqVO reqVO) {
        userService.updateUserStatus(reqVO.getId(), reqVO.getStatus());
        return success(true);
    }

    @GetMapping("/list-all-simple")
    @ApiOperation(value = "获取用户精简信息列表", notes = "只包含被开启的用户，主要用于前端的下拉选项")
    public CommonResult<List<UserSimpleRespVO>> getSimpleUsers() {
        // 获用户门列表，只要开启状态的
        List<UserSimpleRespVO> list = userService.getUsersByStatus(CommonStatusEnum.ENABLE.getStatus());
        return success(list);
    }

    @GetMapping("/getUserInfo")
    @ApiOperation("获得用户详情")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1540614322441457665", dataTypeClass = Long.class)
    public CommonResult<UserRespVO> getUserInfo(@RequestParam("id") Long id) {
        return success(userService.getUserInfo(id));
    }

}
