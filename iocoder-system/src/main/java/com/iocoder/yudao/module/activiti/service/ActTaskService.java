package com.iocoder.yudao.module.activiti.service;

import com.iocoder.yudao.module.activiti.dto.ProcessResultDTO;
import com.iocoder.yudao.module.activiti.dto.instance.ProcessTaskCompleteDTO;
import com.iocoder.yudao.module.activiti.dto.task.ActTaskDTO;

import java.util.List;
import java.util.Map;

/**
 * @author wu kai
 * @since 2022/7/13
 */
public interface ActTaskService {

    /**
     * 根据流程实例id 获取当前任务
     *
     * @param processInstanceId 流程实例id
     * @return 任务集合
     */
    List<ActTaskDTO> getCurrentTask(String processInstanceId);


    /**
     * 完成任务
     *
     * @param processTaskCompleteDTO 完成任务参数
     * @return 完成结果
     */
    ProcessResultDTO completeTask(ProcessTaskCompleteDTO processTaskCompleteDTO);

    /**
     * 完成任务，带流程变量
     *
     * @param taskId       任务ID
     * @param handleUserId 当前任务处理人
     * @param variables    变量参数
     * @return 完成结果
     */
    ActTaskDTO completeTask0(String taskId, String handleUserId, Map<String, Object> variables);
}
