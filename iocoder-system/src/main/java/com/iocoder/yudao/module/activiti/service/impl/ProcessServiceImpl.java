package com.iocoder.yudao.module.activiti.service.impl;

import com.iocoder.yudao.module.activiti.dto.instance.ProcessInstanceDTO;
import com.iocoder.yudao.module.activiti.dto.ProcessResultDTO;
import com.iocoder.yudao.module.activiti.dto.task.ActTaskDTO;
import com.iocoder.yudao.module.activiti.dto.task.ProcessTaskTodoDTO;
import com.iocoder.yudao.module.activiti.service.ActInstanceService;
import com.iocoder.yudao.module.activiti.service.ActTaskService;
import com.iocoder.yudao.module.activiti.service.BpmProcessDefinitionService;
import com.iocoder.yudao.module.activiti.service.ProcessService;
import com.iocoder.yudao.module.commons.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.iocoder.yudao.module.commons.constant.ErrorCodeConstants.PROCESS_DEFINITION_IS_SUSPENDED;
import static com.iocoder.yudao.module.commons.constant.ErrorCodeConstants.PROCESS_DEFINITION_NOT_EXISTS;
import static com.iocoder.yudao.module.commons.exception.ServiceExceptionUtil.exception;

/**
 * @author wu kai
 * @since 2022/7/13
 */

@Service
@Slf4j
public class ProcessServiceImpl implements ProcessService {

    @Resource
    ActInstanceService actInstanceService;

    @Resource
    BpmProcessDefinitionService processDefinitionService;

    @Resource
    RuntimeService runtimeService;

    @Resource
    ActTaskService actTaskService;

    @Override
    public ProcessResultDTO startInstance(ProcessInstanceDTO processInstanceDTO) {
        // 获取流程定义
        ProcessDefinition definition = processDefinitionService.getActiveProcessDefinition(processInstanceDTO.getProcessDefinitionKey());
        // 校验流程定义
        checkProcessInstance(definition);
        log.info("流程实例启动，流程定义key：{}", processInstanceDTO.getProcessDefinitionKey());
        try {
            // 流程实例启动
            ProcessInstance processInstance = actInstanceService.startProcessInsByDefId(definition.getId(), processInstanceDTO.getBusinessKey(), processInstanceDTO.getVariables());
            // 设置流程名
            runtimeService.setProcessInstanceName(processInstance.getId(), definition.getName());

            // 返回代办任务信息
            ProcessTaskTodoDTO processTaskTodoDTO = new ProcessTaskTodoDTO();
            processTaskTodoDTO.setInstanceId(processInstance.getId());
            processTaskTodoDTO.setBusinessId(processInstanceDTO.getBusinessKey());
            processTaskTodoDTO.setRemarks(processInstanceDTO.getRemarks());

            // 添加流程下一个节点的代办
            List<ProcessResultDTO> nextNodeTodoList = addProcessNextNodeTodo(processTaskTodoDTO);
            return null;
        } catch (Exception e) {
            log.error("流程实例启动失败，失败流程定义key:{}，异常信息为:{}", processInstanceDTO.getProcessDefinitionKey(), e.getMessage());
            throw new RuntimeException("流程实例启动失败，异常信息为:{}", e);
        }
    }

    /**
     * 添加下一节点的处理人代办
     *
     * @param processTaskTodoDTO 流程任务代办参数
     * @return 下一节点的处理人代办
     */
    private List<ProcessResultDTO> addProcessNextNodeTodo(ProcessTaskTodoDTO processTaskTodoDTO) {
        log.info("流程任务代办添加，流程实例Id:{}，业务Id:{}", processTaskTodoDTO.getInstanceId(), processTaskTodoDTO.getBusinessId());
        Map<String, ProcessResultDTO> todoMap = new HashMap<>();
        // 获取当前任务列表
        List<ActTaskDTO> currentTask = actTaskService.getCurrentTask(processTaskTodoDTO.getInstanceId());
        return Collections.emptyList();
    }

    /**
     * 校验流程实例
     *
     * @param definition 流程实例信息
     */
    private void checkProcessInstance(ProcessDefinition definition) {
        // 校验流程定义是否存在
        if (definition == null) {
            throw exception(PROCESS_DEFINITION_NOT_EXISTS);
        }
        // 校验流程定义是否挂起
        if (definition.isSuspended()) {
            throw exception(PROCESS_DEFINITION_IS_SUSPENDED);
        }
    }
}
