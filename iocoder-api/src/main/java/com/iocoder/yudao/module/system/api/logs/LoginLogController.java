package com.iocoder.yudao.module.system.api.logs;


import com.iocoder.yudao.module.commons.annotation.Log;
import com.iocoder.yudao.module.commons.core.domain.CommonResult;
import com.iocoder.yudao.module.commons.core.domain.PageResult;
import com.iocoder.yudao.module.commons.enums.BusinessType;
import com.iocoder.yudao.module.framework.config.web.auth.service.LoginLogService;
import com.iocoder.yudao.module.system.vo.logs.loginlog.LoginLogBatchDeleteReqVO;
import com.iocoder.yudao.module.system.vo.logs.loginlog.LoginLogPageReqVO;
import com.iocoder.yudao.module.system.vo.logs.loginlog.LoginLogRespVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static com.iocoder.yudao.module.commons.core.domain.CommonResult.success;

/**
 * <p>
 * 系统访问记录 前端控制器
 * </p>
 *
 * @author wu kai
 * @since 2022-06-22
 */
@Api(tags = "管理后台 - 登录日志")
@RestController
@RequestMapping("/system/login-logs")
@Validated
public class LoginLogController {

    @Resource
    LoginLogService loginLogService;

    @GetMapping("/page")
    @ApiOperation("获得登录日志分页列表")
    @PreAuthorize("@ss.hasPermission('system:login-logs:query')")
    public CommonResult<PageResult<LoginLogRespVO>> getLoginLogPage(@Valid LoginLogPageReqVO pageReqVO) {
        return success(loginLogService.getLoginLogPage(pageReqVO));
    }

    @Log(title = "登录日志", businessType = BusinessType.DELETE)
    @PreAuthorize("@ss.hasPermission('system:login-logs:delete')")
    @DeleteMapping("/delete-login-logs-batch")
    @ApiOperation("批量删除登录日志")
    public CommonResult<Boolean> deleteLogsBatch(@Valid @RequestBody LoginLogBatchDeleteReqVO batchDeleteReqVO) {
        loginLogService.deleteLogsBatch(batchDeleteReqVO);
        return success(true);
    }

    @PreAuthorize("@ss.hasPermission('system:login-logs:delete')")
    @DeleteMapping("/truncate-logs")
    @ApiOperation("清空")
    public CommonResult<Boolean> truncateLogs() {
        loginLogService.truncateLogs();
        return success(true);
    }
}
