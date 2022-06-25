package com.iocoder.yudao.module.system.api;


import com.iocoder.yudao.module.commons.core.domain.CommonResult;
import com.iocoder.yudao.module.commons.core.domain.PageResult;
import com.iocoder.yudao.module.commons.core.domain.UserDO;
import com.iocoder.yudao.module.commons.utils.BeanUtil;
import com.iocoder.yudao.module.commons.utils.convert.CollConvertUtils;
import com.iocoder.yudao.module.system.domain.DeptDO;
import com.iocoder.yudao.module.system.domain.PostDO;
import com.iocoder.yudao.module.system.mapper.UserPostMapper;
import com.iocoder.yudao.module.system.service.*;
import com.iocoder.yudao.module.system.vo.user.UserCreateReqVO;
import com.iocoder.yudao.module.system.vo.user.UserPageItemRespVO;
import com.iocoder.yudao.module.system.vo.user.UserPageQueryRequestVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

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
}
