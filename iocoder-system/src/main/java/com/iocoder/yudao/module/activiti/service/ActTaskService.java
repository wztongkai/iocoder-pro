package com.iocoder.yudao.module.activiti.service;

import com.iocoder.yudao.module.activiti.dto.task.ActTaskDTO;

import java.util.List;

/**
 * @author wu kai
 * @since 2022/7/13
 */
public interface ActTaskService {

    /**
     * 根据流程实例id 获取当前任务
     * @param processInstanceId 流程实例id
     * @return 任务集合
     */
    List<ActTaskDTO> getCurrentTask(String processInstanceId);

}
