package com.iocoder.yudao.module.activiti.service.impl;

import com.iocoder.yudao.module.activiti.dto.instance.ActProcessInstanceDTO;
import com.iocoder.yudao.module.activiti.service.ActInstanceService;
import com.iocoder.yudao.module.commons.config.Assertion;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;

/**
 * @author wu kai
 * @since 2022/7/13
 */
@Service
@Slf4j
public class ActInstanceServiceImpl implements ActInstanceService {

    @Resource
    RuntimeService runtimeService;

    @Resource
    HistoryService historyService;

    @Resource
    TaskService taskService;

    @Override
    public ProcessInstance startProcessInsByDefId(String processDefinitionId, String businessKey, Map<String, Object> variables) {

        try {
            return runtimeService.startProcessInstanceById(processDefinitionId, businessKey, variables);
        } catch (Exception e) {
            log.error("流程实例启动失败，异常信息为:{}", e.getMessage());
            throw new RuntimeException("流程实例启动失败，异常信息为:{}", e);
        }
    }

    @Override
    public ActProcessInstanceDTO getProcessInstanceById(String processInstanceId) {
        ActProcessInstanceDTO dto;
        // 校验流程是否完成
        boolean processFinished = isProcessFinished(processInstanceId);
        if (processFinished) {
            // 获取历史流程实例
            HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
            dto = ActProcessInstanceDTO.builder()
                    .instanceId(historicProcessInstance.getId())
                    .processDefinitionId(historicProcessInstance.getProcessDefinitionId())
                    .processDefinitionKey(historicProcessInstance.getProcessDefinitionKey())
                    .build();
        } else {
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
            dto = ActProcessInstanceDTO.builder()
                    .instanceId(processInstance.getId())
                    .processDefinitionId(processInstance.getProcessDefinitionId())
                    .processDefinitionKey(processInstance.getProcessDefinitionKey())
                    .build();
        }
        return dto;
    }

    @Override
    public boolean isProcessFinished(String instanceId) {
        // 查询正在运行中的流程实例
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(instanceId).singleResult();
        if (ObjectUtils.isEmpty(processInstance)) {
            // 查询历史流程实例
            HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(instanceId).singleResult();
            return ObjectUtils.isNotEmpty(historicProcessInstance);
        }
        return false;
    }

    @Override
    public boolean isSuspendInstanceByTaskId(String taskId) {
        Task task = taskService
                .createTaskQuery()
                .taskId(taskId)
                .singleResult();
        Assertion.isNull(task,"未查询到指定任务");
        String processInstanceId = task.getProcessInstanceId();
        return this.isSuspendInstance(processInstanceId);
    }

    private boolean isSuspendInstance(String processInstanceId) {
        // 判断当前流程是否已经完结，如果完结直接返回否
        if (isProcessFinished(processInstanceId)) {
            return false;
        }
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        if (Objects.isNull(processInstance)) {
            log.error("查询流程是否挂起时：根据当前流程实例ID{}，为查询到指定的流程实例", processInstanceId);
            throw new ActivitiException("根据当前实例ID，未查询到指定流程实例");
        }
        return processInstance.isSuspended();
    }
}
