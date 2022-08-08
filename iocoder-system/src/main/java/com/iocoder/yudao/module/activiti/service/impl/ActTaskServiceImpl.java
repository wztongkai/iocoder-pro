package com.iocoder.yudao.module.activiti.service.impl;

import com.iocoder.yudao.module.activiti.dto.instance.ActProcessInstanceDTO;
import com.iocoder.yudao.module.activiti.dto.task.ActTaskDTO;
import com.iocoder.yudao.module.activiti.service.ActInstanceService;
import com.iocoder.yudao.module.activiti.service.ActTaskService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 流程任务接口
 *
 * @author wu kai
 * @since 2022/7/13
 */
@Service
public class ActTaskServiceImpl implements ActTaskService {

    @Resource
    TaskService taskService;

    @Resource
    ActInstanceService actInstanceService;

    @Override
    public List<ActTaskDTO> getCurrentTask(String processInstanceId) {
        // 获取任务列表
        List<Task> taskList = taskService.createTaskQuery().processInstanceId(processInstanceId).active().list();
        // 获取任务详情
        return taskList.stream().map(this::getTaskInfo).collect(Collectors.toList());
    }

    /**
     * 获取任务详情
     * @param task 任务信息
     * @return 任务详情
     */
    private ActTaskDTO getTaskInfo(Task task) {
        ActTaskDTO taskDTO = new ActTaskDTO();
        taskDTO.setTaskId(task.getId());
        taskDTO.setTaskName(task.getName());
        taskDTO.setTaskKey(task.getTaskDefinitionKey());
        // 获取当前处理人
        List<String> todoUsers;
        String assignee = task.getAssignee();
        if (StringUtils.isNotEmpty(assignee)) {
            todoUsers = Collections.singletonList(assignee);
        } else {
            todoUsers = getTaskCandidate(task.getId());
        }
        taskDTO.setTodoUserList(todoUsers);
        // 获取流程实例信息
        ActProcessInstanceDTO instanceDTO = actInstanceService.getProcessInstanceById(task.getProcessInstanceId());
        taskDTO.setProcessInstanceId(instanceDTO.getInstanceId());
        taskDTO.setProcessDefinitionId(instanceDTO.getProcessDefinitionId());
        taskDTO.setProcessDefinitionKey(instanceDTO.getProcessDefinitionKey());
        return taskDTO;
    }

    /**
     * 获取当前任务节点下的候选用户（不包含候选组）
     *
     * @param taskId 任务ID
     * @return 用户ID集合
     */
    private List<String> getTaskCandidate(String taskId) {
        List<String> users = new ArrayList<>();
        List<IdentityLink> links = taskService.getIdentityLinksForTask(taskId);
        if (!CollectionUtils.isEmpty(links)) {
            users = links
                    .stream()
                    .map(IdentityLink::getUserId)
                    .filter(StringUtils::isNotBlank)
                    .distinct()
                    .collect(Collectors.toList());
        }
        return users;
    }
}
