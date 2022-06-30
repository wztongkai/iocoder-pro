package com.iocoder.yudao.module.framework.config.web.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iocoder.yudao.module.commons.core.domain.PageResult;
import com.iocoder.yudao.module.commons.exception.ServiceExceptionUtil;
import com.iocoder.yudao.module.commons.utils.BeanUtil;
import com.iocoder.yudao.module.framework.config.web.auth.dto.LoginLogCreateReqDTO;
import com.iocoder.yudao.module.framework.config.web.auth.service.LoginLogService;
import com.iocoder.yudao.module.system.domain.LoginLogDO;
import com.iocoder.yudao.module.system.mapper.LoginLogMapper;
import com.iocoder.yudao.module.system.vo.logs.loginlog.LoginLogBatchDeleteReqVO;
import com.iocoder.yudao.module.system.vo.logs.loginlog.LoginLogPageReqVO;
import com.iocoder.yudao.module.system.vo.logs.loginlog.LoginLogRespVO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.iocoder.yudao.module.commons.constant.ErrorCodeConstants.LogErrorCode.LOG_NOT_FOUND;
import static com.iocoder.yudao.module.commons.constant.ErrorCodeConstants.REQ_ARGS_NOT_NULL;

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
        BeanUtil.copyProperties(reqDTO, loginLogDO);
        baseMapper.insert(loginLogDO);
    }

    @Override
    public PageResult<LoginLogRespVO> getLoginLogPage(LoginLogPageReqVO pageReqVO) {
        // 查询日志分页列表
        PageResult<LoginLogDO> loginLogPage = baseMapper.selectLoginLogPage(pageReqVO);
        // 组装返回数据
        List<LoginLogRespVO> loginLogList = new ArrayList<>(loginLogPage.getList().size());
        BeanUtil.copyListProperties(loginLogPage.getList(), loginLogList, LoginLogRespVO.class);
        return new PageResult<>(loginLogList, loginLogPage.getTotal());
    }

    @Override
    public void deleteLoginLog(Long logId) {
        // 校验日志存在
        checkLogExist(logId);
        // 删除用户基本信息
        baseMapper.deleteById(logId);
    }

    @Override
    public void deleteLogsBatch(LoginLogBatchDeleteReqVO batchDeleteReqVO) {
        if (CollectionUtils.isEmpty(batchDeleteReqVO.getLogIds())) {
            throw ServiceExceptionUtil.exception(REQ_ARGS_NOT_NULL);
        }
        batchDeleteReqVO.getLogIds().forEach(this::deleteLoginLog);
    }

    @Override
    public void truncateLogs() {
        baseMapper.truncateLogs();
    }

    private void checkLogExist(Long logId) {
        if (logId == null) {
            return;
        }
        LoginLogDO loginLogDO = baseMapper.selectById(logId);
        if (ObjectUtils.isEmpty(loginLogDO)) {
            throw ServiceExceptionUtil.exception(LOG_NOT_FOUND);
        }
    }
}
