package com.iocoder.yudao.module.activiti.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.iocoder.yudao.module.activiti.dto.task.ActTaskDTO;
import com.iocoder.yudao.module.activiti.service.ActHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.HistoryService;
import org.activiti.engine.history.HistoricIdentityLink;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 历史流程相关接口
 *
 * @author wu kai
 * @since 2022/10/8
 */
@Service
@Slf4j
public class ActHistoryServiceImpl implements ActHistoryService {

    @Resource
    HistoryService historyService;

    @Override
    public ActTaskDTO getHistoricTaskById(String taskId) {
        HistoricTaskInstance taskInstance = historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
        if (ObjectUtil.isEmpty(taskInstance)) {
            return null;
        }
        return this.getHistoricTaskInfo(taskInstance);
    }

    @Override
    public ActTaskDTO getHistoricTaskInfo(HistoricTaskInstance historicTaskInstance) {
        ActTaskDTO taskDTO = new ActTaskDTO();
        // 获取Task信息
        taskDTO.setTaskId(historicTaskInstance.getId());
        taskDTO.setTaskName(historicTaskInstance.getName());
        taskDTO.setTaskKey(historicTaskInstance.getTaskDefinitionKey());
        // 获取当前办理人
        List<String> todoUsers;
        String assignee = historicTaskInstance.getAssignee();
        if (StringUtils.isNotEmpty(assignee)) {
            todoUsers = Collections.singletonList(assignee);
        } else {
            // 获取当前历史任务节点下的候选用户
            todoUsers = getHistoricTaskCandidate(historicTaskInstance.getId());
        }
        taskDTO.setTodoUserList(todoUsers);
        // 填充流程实例信息
        String processInstanceId = historicTaskInstance.getProcessInstanceId();
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
                .processInstanceId(processInstanceId).singleResult();
        taskDTO.setProcessInstanceId(historicProcessInstance.getId());
        taskDTO.setProcessDefinitionId(historicProcessInstance.getProcessDefinitionId());
        taskDTO.setProcessDefinitionKey(historicProcessInstance.getProcessDefinitionKey());
        return taskDTO;
    }

    @Override
    public ActTaskDTO getPreviousTaskNodeInfo(String instanceId) {
        List<HistoricTaskInstance> list = historyService
                .createHistoricTaskInstanceQuery()
                .processInstanceId(instanceId)
                .orderByHistoricTaskInstanceEndTime()
                .desc()
                .list();
        HistoricTaskInstance taskInstance = null;
        if (!list.isEmpty() && list.get(0).getEndTime() != null) {
            taskInstance = list.get(0);
        }
        if (ObjectUtils.isEmpty(taskInstance)) {
            return null;
        }
        // 获取节点详细信息
        return this.getHistoricTaskInfo(taskInstance);
    }

    /**
     * 获取当前历史任务节点下的候选用户（不包含候选组）
     *
     * @param taskId 任务ID
     * @return 用户ID集合
     */
    private List<String> getHistoricTaskCandidate(String taskId) {
        List<String> users = new ArrayList<>();
        List<HistoricIdentityLink> historicLinks = historyService.getHistoricIdentityLinksForTask(taskId);
        if (!CollectionUtils.isEmpty(historicLinks)) {
            users = historicLinks.stream().map(HistoricIdentityLink::getUserId)
                    .filter(StringUtils::isNotEmpty)
                    .distinct()
                    .collect(Collectors.toList());
        }
        return users;
    }
}
