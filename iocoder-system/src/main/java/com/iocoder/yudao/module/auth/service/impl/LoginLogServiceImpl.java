package com.iocoder.yudao.module.auth.service.impl;

import com.iocoder.yudao.module.auth.dto.LoginLogCreateReqDTO;
import com.iocoder.yudao.module.commons.utils.BeanUtil;
import com.iocoder.yudao.module.system.domain.LoginLogDO;
import com.iocoder.yudao.module.system.mapper.LoginLogMapper;
import com.iocoder.yudao.module.auth.service.LoginLogService;
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

    @Override
    public void createLoginLog(LoginLogCreateReqDTO reqDTO) {
        LoginLogDO loginLogDO = new LoginLogDO();
        BeanUtil.copyProperties(reqDTO,loginLogDO);
        baseMapper.insert(loginLogDO);
    }
}
