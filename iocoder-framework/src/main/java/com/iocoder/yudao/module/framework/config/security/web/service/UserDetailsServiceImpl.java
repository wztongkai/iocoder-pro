package com.iocoder.yudao.module.framework.config.security.web.service;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.iocoder.yudao.module.commons.core.domain.LoginUser;
import com.iocoder.yudao.module.commons.core.domain.UserDO;
import com.iocoder.yudao.module.commons.enums.user.UserStatus;
import com.iocoder.yudao.module.commons.exception.ServiceException;
import com.iocoder.yudao.module.commons.utils.StringUtils;
import com.iocoder.yudao.module.system.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户验证处理
 *
 * @author kai wu
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Resource
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDO userDO = this.userService.getOne(new LambdaQueryWrapper<UserDO>()
                .eq(UserDO::getUsername, username)
                .orderByDesc(UserDO::getCreateTime).last("limit 1")
        );
        if (StringUtils.isNull(userDO)) {
            log.info("登录用户：{} 不存在.", username);
            throw new ServiceException("登录用户：" + username + " 不存在");
        } else if (UserStatus.DISABLE.getCode().equals(userDO.getStatus())) {
            log.info("登录用户：{} 已被停用.", username);
            throw new ServiceException("对不起，您的账号：" + username + " 已停用");
        }

        return this.createLoginUser(userDO);
    }

    public UserDetails createLoginUser(UserDO userDO) {
        return new LoginUser(userDO.getId(), userDO);
    }
}
