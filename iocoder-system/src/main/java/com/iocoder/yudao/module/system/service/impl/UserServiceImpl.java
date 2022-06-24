package com.iocoder.yudao.module.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.iocoder.yudao.module.commons.core.domain.PageResult;
import com.iocoder.yudao.module.commons.core.domain.UserDO;
import com.iocoder.yudao.module.system.vo.user.UserPageQueryRequestVo;
import com.iocoder.yudao.module.system.mapper.UserMapper;
import com.iocoder.yudao.module.system.service.DeptService;
import com.iocoder.yudao.module.system.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;

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

    @Override
    public void updateUserLogin(Long userId, String loginIp) {
        baseMapper.update(null, new LambdaUpdateWrapper<UserDO>()
                .set(UserDO::getLoginIp, loginIp)
                .set(UserDO::getLoginDate, LocalDate.now())
                .eq(UserDO::getId, userId)
        );
    }
}
