package com.iocoder.yudao.module.system.api;


import com.iocoder.yudao.module.commons.core.domain.CommonResult;
import com.iocoder.yudao.module.commons.core.domain.PageResult;
import com.iocoder.yudao.module.commons.utils.BeanUtil;
import com.iocoder.yudao.module.commons.utils.convert.CollConvertUtils;
import com.iocoder.yudao.module.system.domain.DeptDO;
import com.iocoder.yudao.module.commons.core.domain.UserDO;
import com.iocoder.yudao.module.system.service.DeptService;
import com.iocoder.yudao.module.system.service.UserService;
import com.iocoder.yudao.module.system.vo.user.UserPageItemRespVO;
import com.iocoder.yudao.module.system.vo.user.UserPageQueryRequestVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    DeptService deptService;

    @GetMapping("/getUserPage")
    @ApiOperation("获得用户分页列表")
    public CommonResult<PageResult<UserPageItemRespVO>> getUserPage(@Valid UserPageQueryRequestVo requestVo){
        // 获得用户分页列表
        PageResult<UserDO> pageResult = userService.selectUserList(requestVo);
        if(CollectionUtils.isEmpty(pageResult.getList())){
            return success(new PageResult<>(pageResult.getTotal()));
        }
        // 获取所有部门id
        Collection<Long> deptIds = CollConvertUtils.convertSet(pageResult.getList(), UserDO::getDeptId);
        // 获取部门信息
        Map<Long,DeptDO> deptInfoMap = deptService.getDeptInfoMap(deptIds);
        List<UserPageItemRespVO> userList = new ArrayList<>(pageResult.getList().size());
        pageResult.getList().forEach(userInfo -> {
            UserPageItemRespVO userPageItemRespVO = new UserPageItemRespVO();
            BeanUtil.copyProperties(userInfo,userPageItemRespVO);
            UserPageItemRespVO.DeptDO uDept = new UserPageItemRespVO.DeptDO();
            DeptDO deptDO = deptInfoMap.get(userInfo.getDeptId());
            BeanUtil.copyProperties(deptDO,uDept);
            userPageItemRespVO.setDept(uDept);
            userList.add(userPageItemRespVO);
        });
        return success(new PageResult<>(userList, pageResult.getTotal()));
    }
}
