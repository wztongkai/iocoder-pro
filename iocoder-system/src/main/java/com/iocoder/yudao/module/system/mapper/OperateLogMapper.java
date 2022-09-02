package com.iocoder.yudao.module.system.mapper;

import com.iocoder.yudao.module.commons.core.LambdaQueryWrapperX;
import com.iocoder.yudao.module.commons.core.domain.PageResult;
import com.iocoder.yudao.module.commons.core.mapper.BaseMapperX;
import com.iocoder.yudao.module.commons.enums.common.ResultsEnum;
import com.iocoder.yudao.module.system.domain.OperateLogDO;
import com.iocoder.yudao.module.system.vo.logs.operatelog.OperateLogPageReqVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.Collection;

/**
 * <p>
 * 操作日志记录 Mapper 接口
 * </p>
 *
 * @author wu kai
 * @since 2022-06-22
 */
@Mapper
public interface OperateLogMapper extends BaseMapperX<OperateLogDO> {

    /**
     * 查询操作日志列表
     *
     * @param logPageReqVO 筛选信息
     * @param userIds      用户编号
     * @return 日志列表
     */
    default PageResult<OperateLogDO> selectOperateLogPage(OperateLogPageReqVO logPageReqVO, Collection<Long> userIds) {
        // 构造查询条件
        LambdaQueryWrapperX<OperateLogDO> query = new LambdaQueryWrapperX<OperateLogDO>()
                .likeIfPresent(OperateLogDO::getModule, logPageReqVO.getModule())
                .inIfPresent(OperateLogDO::getUserId, userIds)
                .eqIfPresent(OperateLogDO::getType, logPageReqVO.getType())
                .betweenIfPresent(OperateLogDO::getStartTime, logPageReqVO.getCreateTime());
        if (Boolean.TRUE.equals(logPageReqVO.getStatus())) {
            query.eq(OperateLogDO::getResultCode, ResultsEnum.SUCCEED.getCode());
        } else if (Boolean.FALSE.equals(logPageReqVO.getStatus())) {
            query.eq(OperateLogDO::getResultCode, ResultsEnum.FAILURE.getCode());
        }
        query.orderByDesc(OperateLogDO::getCreateTime);
        // 执行查询
        return selectPage(logPageReqVO, query);
    }

    /**
     * 清空操作日志表
     */
    @Update("truncate table system_operate_log")
    void truncateLogs();
}
