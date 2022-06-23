package com.iocoder.yudao.module.system.service.impl;

import com.iocoder.yudao.module.system.domain.LoginLogDO;
import com.iocoder.yudao.module.system.mapper.LoginLogMapper;
import com.iocoder.yudao.module.system.service.LoginLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统访问记录 服务实现类
 * </p>
 *
 * @author wu kai
 * @since 2022-06-22
 */
@Service
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLogDO> implements LoginLogService {

}
