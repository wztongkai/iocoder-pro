package com.iocoder.yudao.module.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iocoder.yudao.module.commons.core.domain.PageResult;
import com.iocoder.yudao.module.commons.core.domain.UserDO;
import com.iocoder.yudao.module.commons.exception.ServiceExceptionUtil;
import com.iocoder.yudao.module.commons.utils.BeanUtil;
import com.iocoder.yudao.module.commons.utils.StringUtils;
import com.iocoder.yudao.module.commons.utils.convert.CollConvertUtils;
import com.iocoder.yudao.module.system.domain.OperateLogDO;
import com.iocoder.yudao.module.system.mapper.OperateLogMapper;
import com.iocoder.yudao.module.system.service.OperateLogService;
import com.iocoder.yudao.module.system.service.UserService;
import com.iocoder.yudao.module.system.vo.logs.operatelog.OperateLogBatchDeleteReqVO;
import com.iocoder.yudao.module.system.vo.logs.operatelog.OperateLogPageReqVO;
import com.iocoder.yudao.module.system.vo.logs.operatelog.OperateLogRespVO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.iocoder.yudao.module.commons.constant.ErrorCodeConstants.LogErrorCode.LOG_NOT_FOUND;
import static com.iocoder.yudao.module.commons.constant.ErrorCodeConstants.REQ_ARGS_NOT_NULL;

/**
 * <p>
 * 操作日志记录 服务实现类
 * </p>
 *
 * @author wu kai
 * @since 2022-06-22
 */
@Service
public class OperateLogServiceImpl extends ServiceImpl<OperateLogMapper, OperateLogDO> implements OperateLogService {

    @Resource
    UserService userService;

    @Override
    public PageResult<OperateLogRespVO> selectOperateLogPage(OperateLogPageReqVO logPageReqVO) {
        // 处理用户昵称
        Collection<Long> userIds = null;
        if (StringUtils.isNotEmpty(logPageReqVO.getUserNickname())) {
            List<UserDO> userList = userService.selectUserByNickName(logPageReqVO.getUserNickname());
            userIds = CollConvertUtils.convertSet(userList, UserDO::getId);
            if (CollectionUtils.isEmpty(userIds)) {
                return PageResult.empty();
            }
        }
        // 查询操作日志列表
        PageResult<OperateLogDO> operateLogPage = baseMapper.selectOperateLogPage(logPageReqVO, userIds);
        List<OperateLogRespVO> voArrayList = new ArrayList<>(operateLogPage.getList().size());
        BeanUtil.copyListProperties(operateLogPage.getList(), voArrayList, OperateLogRespVO.class);
        if(CollectionUtils.isNotEmpty(voArrayList)){
            voArrayList.forEach(operateLogRespVO -> {
                UserDO userDO = userService.getById(operateLogRespVO.getUserId());
                operateLogRespVO.setUserNickname(userDO.getUsername());
            });
        }
        return new PageResult<>(voArrayList, operateLogPage.getTotal());
    }

    @Override
    public void deleteLoginLog(Long logId) {
        // 校验日志存在
        checkLogExist(logId);
        // 删除用户基本信息
        baseMapper.deleteById(logId);
    }

    @Override
    public void deleteLogsBatch(OperateLogBatchDeleteReqVO batchDeleteReqVO) {
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
        OperateLogDO operateLogDO = baseMapper.selectById(logId);
        if (ObjectUtils.isEmpty(operateLogDO)) {
            throw ServiceExceptionUtil.exception(LOG_NOT_FOUND);
        }
    }
}
