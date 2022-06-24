package com.iocoder.yudao.module.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iocoder.yudao.module.auth.dto.LoginLogCreateReqDTO;
import com.iocoder.yudao.module.system.domain.LoginLogDO;

import javax.validation.Valid;

/**
 * <p>
 * 系统访问记录 服务类
 * </p>
 *
 * @author wu kai
 * @since 2022-06-22
 */
public interface LoginLogService extends IService<LoginLogDO> {
    /**
     * 创建登录日志
     *
     * @param reqDTO 日志信息
     */
    void createLoginLog(@Valid LoginLogCreateReqDTO reqDTO);
}
