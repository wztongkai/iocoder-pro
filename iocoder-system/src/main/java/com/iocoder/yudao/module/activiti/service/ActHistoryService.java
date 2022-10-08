package com.iocoder.yudao.module.activiti.service;

import com.iocoder.yudao.module.activiti.dto.task.ActTaskDTO;
import org.activiti.engine.history.HistoricTaskInstance;

/**
 * 历史流程相关接口
 *
 * @author wu kai
 * @since 2022/10/8
 */
public interface ActHistoryService {

    /**
     * 根据任务ID，获取历史任务信息
     *
     * @param taskId 流程任务ID
     * @return 包装的Task信息
     */
    ActTaskDTO getHistoricTaskById(String taskId);

    /**
     * 获取历史任务详情
     *
     * @param historicTaskInstance 历史任务信息
     * @return 任务详情
     */
    ActTaskDTO getHistoricTaskInfo(HistoricTaskInstance historicTaskInstance);

    /**
     * 获取流程上一节点信息
     *
     * @param instanceId 流程实例
     * @return 上一节点信息
     */
    ActTaskDTO getPreviousTaskNodeInfo(String instanceId);
}
