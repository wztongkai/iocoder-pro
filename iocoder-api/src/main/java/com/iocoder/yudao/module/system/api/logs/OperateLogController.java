package com.iocoder.yudao.module.system.api.logs;


import com.iocoder.yudao.module.commons.annotation.Log;
import com.iocoder.yudao.module.commons.core.domain.CommonResult;
import com.iocoder.yudao.module.commons.core.domain.PageResult;
import com.iocoder.yudao.module.commons.enums.BusinessType;
import com.iocoder.yudao.module.system.service.OperateLogService;
import com.iocoder.yudao.module.system.vo.logs.operatelog.OperateLogBatchDeleteReqVO;
import com.iocoder.yudao.module.system.vo.logs.operatelog.OperateLogPageReqVO;
import com.iocoder.yudao.module.system.vo.logs.operatelog.OperateLogRespVO;
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
 * 操作日志记录 前端控制器
 * </p>
 *
 * @author wu kai
 * @since 2022-06-22
 */
@Api(tags = "管理后台 - 操作日志")
@RestController
@RequestMapping("/system/operate-logs")
@Validated
public class OperateLogController {

    @Resource
    OperateLogService operateLogService;

    @GetMapping("/page")
    @ApiOperation("查看操作日志分页列表")
    @PreAuthorize("@ss.hasPermission('system:operate-logs:query')")
    public CommonResult<PageResult<OperateLogRespVO>> pageOperateLog(@Valid OperateLogPageReqVO logPageReqVO) {
        return success(operateLogService.selectOperateLogPage(logPageReqVO));
    }

    @Log(title = "操作日志", businessType = BusinessType.DELETE)
    @PreAuthorize("@ss.hasPermission('system:operate-logs:delete')")
    @DeleteMapping("/delete-operate-logs-batch")
    @ApiOperation("批量删除操作日志")
    public CommonResult<Boolean> deleteLogsBatch(@Valid @RequestBody OperateLogBatchDeleteReqVO batchDeleteReqVO) {
        operateLogService.deleteLogsBatch(batchDeleteReqVO);
        return success(true);
    }

    @PreAuthorize("@ss.hasPermission('system:operate-logs:delete')")
    @DeleteMapping("/truncate-logs")
    @ApiOperation("清空")
    public CommonResult<Boolean> truncateLogs() {
        operateLogService.truncateLogs();
        return success(true);
    }


}
