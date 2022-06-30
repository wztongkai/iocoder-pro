package com.iocoder.yudao.module.system.service;

import com.iocoder.yudao.module.commons.core.domain.PageResult;
import com.iocoder.yudao.module.system.domain.OperateLogDO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.iocoder.yudao.module.system.vo.logs.operatelog.OperateLogBatchDeleteReqVO;
import com.iocoder.yudao.module.system.vo.logs.operatelog.OperateLogPageReqVO;
import com.iocoder.yudao.module.system.vo.logs.operatelog.OperateLogRespVO;

/**
 * <p>
 * 操作日志记录 服务类
 * </p>
 *
 * @author wu kai
 * @since 2022-06-22
 */
public interface OperateLogService extends IService<OperateLogDO> {

    /**
     * 查看操作日志列表
     *
     * @param logPageReqVO 筛选信息
     * @return 操作日志列表
     */
    PageResult<OperateLogRespVO> selectOperateLogPage(OperateLogPageReqVO logPageReqVO);

    /**
     * 删除操作日志
     *
     * @param logId 日志编号
     */
    void deleteLoginLog(Long logId);

    /**
     * 批量删除操作日志
     *
     * @param batchDeleteReqVO 日志编号对象
     */
    void deleteLogsBatch(OperateLogBatchDeleteReqVO batchDeleteReqVO);

    /**
     * 清空操作日志
     */
    void truncateLogs();
}
