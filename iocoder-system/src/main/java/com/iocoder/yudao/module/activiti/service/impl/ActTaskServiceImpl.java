package com.iocoder.yudao.module.activiti.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.iocoder.yudao.module.activiti.dto.ProcessResultDTO;
import com.iocoder.yudao.module.activiti.dto.instance.ActProcessInstanceDTO;
import com.iocoder.yudao.module.activiti.dto.instance.ProcessTaskCompleteDTO;
import com.iocoder.yudao.module.activiti.dto.task.ActTaskDTO;
import com.iocoder.yudao.module.activiti.dto.task.ProcessTaskTodoDTO;
import com.iocoder.yudao.module.activiti.service.ActInstanceService;
import com.iocoder.yudao.module.activiti.service.ActTaskService;
import com.iocoder.yudao.module.activiti.service.ProcessService;
import com.iocoder.yudao.module.commons.config.Assertion;
import com.iocoder.yudao.module.commons.constant.Constants;
import com.iocoder.yudao.module.commons.enums.activiti.BaseEnum;
import com.iocoder.yudao.module.commons.exception.ServiceExceptionUtil;
import com.iocoder.yudao.module.commons.exception.base.BaseException;
import com.iocoder.yudao.module.system.domain.BusTodoDO;
import com.iocoder.yudao.module.system.mapper.BusTodoMapper;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static com.iocoder.yudao.module.commons.constant.ErrorCodeConstants.PROCESS_TODO_USER_NOT_EXISTS;

/**
 * 流程任务接口
 *
 * @author wu kai
 * @since 2022/7/13
 */
@Service
@Slf4j
public class ActTaskServiceImpl implements ActTaskService {

    @Resource
    TaskService taskService;

    @Resource
    ActInstanceService actInstanceService;

    @Resource
    ActTaskService actTaskService;

    @Resource
    BusTodoMapper busTodoMapper;

    @Resource
    ProcessService processService;

    @Override
    public List<ActTaskDTO> getCurrentTask(String processInstanceId) {
        // 获取任务列表
        List<Task> taskList = taskService.createTaskQuery().processInstanceId(processInstanceId).active().list();
        // 获取任务详情
        return taskList.stream().map(this::getTaskInfo).collect(Collectors.toList());
    }

    @Override
    public ProcessResultDTO completeTask(ProcessTaskCompleteDTO processTaskCompleteDTO) {
        String taskId = processTaskCompleteDTO.getTaskId();
        Assertion.isBlank(taskId, "任务id不能为空");
        log.info("完成任务，任务id:{}，任务处理人：{}", processTaskCompleteDTO.getTaskId(), processTaskCompleteDTO.getHandleUserName());
        // 判断当前流程是否处于挂起状态
        boolean isSuspend = actInstanceService.isSuspendInstanceByTaskId(taskId);
        if (!isSuspend) {
            log.error("完成任务异常，编号为：{} 的流程已挂起，无法操作", taskId);
            throw new BaseException("完成任务异常，该流程已挂起，无法操作！");
        }
        try {
            // 完成任务
            ActTaskDTO task = actTaskService.completeTask0(processTaskCompleteDTO.getTaskId(), processTaskCompleteDTO.getHandleUserId(), processTaskCompleteDTO.getVariables());
            String instanceId = task.getProcessInstanceId();

            // 更新代办状态
            LambdaUpdateWrapper<BusTodoDO> wrapper = Wrappers.<BusTodoDO>lambdaUpdate()
                    .set(BusTodoDO::getIsHandle, Constants.ONE)
                    .set(BusTodoDO::getHandleUserId, processTaskCompleteDTO.getHandleUserId())
                    .set(BusTodoDO::getHandleUserName, processTaskCompleteDTO.getHandleUserName())
                    .set(BusTodoDO::getHandleTime, new Date())
                    .eq(BusTodoDO::getTaskId, processTaskCompleteDTO.getTaskId());
            int row = busTodoMapper.update(null, wrapper);
            if(row != Constants.ONE){
                Assertion.message("更新代办状态失败");
            }
            // 判断流程是否已完成
            ProcessResultDTO resultDTO = new ProcessResultDTO();
            boolean isFinished = actInstanceService.isProcessFinished(instanceId);
            if (!isFinished) {
                // 添加下一节点处理人待办事项
                ProcessTaskTodoDTO processTaskTodoDTO = new ProcessTaskTodoDTO();
                processTaskTodoDTO.setInstanceId(instanceId);
                processTaskTodoDTO.setBusinessId(processTaskCompleteDTO.getBusId());
                processTaskTodoDTO.setRemarks(processTaskCompleteDTO.getRemark());
                List<ProcessResultDTO> resultDTOS = processService.addProcessNextNodeTodo(processTaskTodoDTO);
                if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(resultDTOS)) {
                    resultDTO = resultDTOS.get(0);
                }
            } else {
                // 流程已完成
                resultDTO.setInstanceId(instanceId);
                resultDTO.setTaskName(BaseEnum.FLOW_END.getMessage());
                resultDTO.setTaskKey(BaseEnum.FLOW_END.getCode());
            }

            return resultDTO;
        } catch (Exception e) {
            log.error("任务id:{} 的流程任务完成失败，错误信息为：{}", taskId,e.getMessage());
            throw new BaseException("流程任务完成失败！");
        }
    }

    @Override
    public ActTaskDTO completeTask0(String taskId, String handleUserId, Map<String, Object> variables) {
        try {
            Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
            if (task.getAssignee() == null) {
                taskService.claim(taskId, handleUserId);
            }
            if (!CollectionUtils.isEmpty(variables)) {
                taskService.setVariables(taskId, variables);
            }
            ActTaskDTO taskDTO = getTaskInfo(task);
            taskService.complete(taskId);
            return taskDTO;
        } catch (Exception e) {
            log.error("任务id为：{} 的任务完成失败，错误信息为：{}", taskId, e.getMessage());
            throw new ActivitiException("完成当前任务失败", e);
        }
    }

    /**
     * 获取任务详情
     *
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
        if (CollectionUtils.isEmpty(todoUsers)) {
            throw ServiceExceptionUtil.exception(PROCESS_TODO_USER_NOT_EXISTS);
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
