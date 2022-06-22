package com.iocoder.yudao.system.service.impl;

import com.iocoder.yudao.system.domain.UserDO;
import com.iocoder.yudao.system.mapper.UserMapper;
import com.iocoder.yudao.system.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
