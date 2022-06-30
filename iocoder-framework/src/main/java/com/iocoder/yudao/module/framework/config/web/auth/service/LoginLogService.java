package com.iocoder.yudao.module.framework.config.web.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iocoder.yudao.module.commons.core.domain.PageResult;
import com.iocoder.yudao.module.framework.config.web.auth.dto.LoginLogCreateReqDTO;
import com.iocoder.yudao.module.system.domain.LoginLogDO;
import com.iocoder.yudao.module.system.vo.logs.loginlog.LoginLogBatchDeleteReqVO;
import com.iocoder.yudao.module.system.vo.logs.loginlog.LoginLogPageReqVO;
import com.iocoder.yudao.module.system.vo.logs.loginlog.LoginLogRespVO;

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

    /**
     * 分页获取登录日志列表
     *
     * @param pageReqVO 筛选条件
     * @return 登录日志列表
     */
    PageResult<LoginLogRespVO> getLoginLogPage(LoginLogPageReqVO pageReqVO);

    /**
     * 删除登录日志
     *
     * @param logId 日志编号
     */
    void deleteLoginLog(Long logId);

    /**
     * 批量删除登录日志
     *
     * @param batchDeleteReqVO 登录日志编号集合
     */
    void deleteLogsBatch(LoginLogBatchDeleteReqVO batchDeleteReqVO);

    /**
     * 清空登录日志表
     */
    void truncateLogs();

}
