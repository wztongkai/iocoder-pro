package com.iocoder.yudao.system.service.impl;

import com.iocoder.yudao.commons.core.domain.PageResult;
import com.iocoder.yudao.commons.core.domain.UserDO;
import com.iocoder.yudao.system.mapper.UserMapper;
import com.iocoder.yudao.system.service.DeptService;
import com.iocoder.yudao.system.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iocoder.yudao.system.vo.user.UserPageQueryRequestVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author wu kai
 * @since 2022-06-22
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {

    @Resource
    DeptService deptService;

    @Override
    public PageResult<UserDO> selectUserList(UserPageQueryRequestVo requestVo) {

        return baseMapper.selectUserList(requestVo, deptService.getDeptCondition(requestVo.getDeptId()));
    }
}
