package com.iocoder.yudao.module.activiti.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.iocoder.yudao.module.activiti.dto.ProcessResultDTO;
import com.iocoder.yudao.module.activiti.dto.instance.ProcessInstanceDTO;
import com.iocoder.yudao.module.activiti.dto.instance.ProcessSubmitDTO;
import com.iocoder.yudao.module.activiti.dto.instance.ProcessTaskCompleteDTO;
import com.iocoder.yudao.module.activiti.dto.task.ActTaskDTO;
import com.iocoder.yudao.module.activiti.dto.task.ProcessTaskTodoDTO;
import com.iocoder.yudao.module.activiti.service.ActInstanceService;
import com.iocoder.yudao.module.activiti.service.ActTaskService;
import com.iocoder.yudao.module.activiti.service.BpmProcessDefinitionService;
import com.iocoder.yudao.module.activiti.service.ProcessService;
import com.iocoder.yudao.module.commons.config.Assertion;
import com.iocoder.yudao.module.commons.constant.Constants;
import com.iocoder.yudao.module.commons.core.domain.UserDO;
import com.iocoder.yudao.module.commons.exception.base.BaseException;
import com.iocoder.yudao.module.system.domain.BusTodoDO;
import com.iocoder.yudao.module.system.mapper.BusTodoMapper;
import com.iocoder.yudao.module.system.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.stereotype.Service;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;

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

    @Resource
    BusTodoMapper busTodoMapper;

    @Resource
    UserMapper userMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
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
            processTaskTodoDTO.setInstanceId(processInstance.getProcessInstanceId());
            processTaskTodoDTO.setBusinessId(processInstanceDTO.getBusinessKey());
            processTaskTodoDTO.setRemarks(processInstanceDTO.getRemarks());

            // 添加流程下一个节点的代办
            List<ProcessResultDTO> nextNodeTodoList = this.addProcessNextNodeTodo(processTaskTodoDTO);
            return CollectionUtils.isNotEmpty(nextNodeTodoList) ? nextNodeTodoList.get(0) : new ProcessResultDTO();
        } catch (Exception e) {
            log.error("流程实例启动失败，失败流程定义key:{}，异常信息为:{}", processInstanceDTO.getProcessDefinitionKey(), e.getMessage());
            throw new RuntimeException("流程实例启动失败，异常信息为:{}", e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProcessResultDTO submit(ProcessSubmitDTO processSubmitDTO) {
        String processDefinitionKey = processSubmitDTO.getProcessDefinitionKey();
        String submitById = processSubmitDTO.getSubmitById();
        Assertion.isBlank(processDefinitionKey, "流程定义key不能为空");
        Assertion.isBlank(submitById, "提交人id不能为空");
        UserDO userDO = userMapper.selectById(submitById);
        Assertion.isNull(userDO, "未查询到提交人信息");

        log.info("流程实例提交，流程定义key：{}", processDefinitionKey);

        try {
            // 启动历程实例
            ProcessInstanceDTO processInstanceDTO = new ProcessInstanceDTO();
            BeanUtils.copyProperties(processSubmitDTO, processInstanceDTO);
            ProcessResultDTO resultDTO = this.startInstance(processInstanceDTO);

            // 完成任务参数配置
            ProcessTaskCompleteDTO processTaskCompleteDTO = new ProcessTaskCompleteDTO();
            processTaskCompleteDTO.setBusId(processSubmitDTO.getBusinessKey());
            processTaskCompleteDTO.setTaskId(resultDTO.getTaskId());
            processTaskCompleteDTO.setVariables(processSubmitDTO.getVariables());
            processTaskCompleteDTO.setHandleUserId(submitById);
            processTaskCompleteDTO.setHandleUserName(userDO.getUsername());
            processTaskCompleteDTO.setHandleResult("提交完成");
            processTaskCompleteDTO.setRemark(processSubmitDTO.getRemark());

            // 调用完成任务接口
            return actTaskService.completeTask(processTaskCompleteDTO);
        } catch (BeansException e) {
            log.error("流程定义key为：{} 的流程提交失败，错误信息为：{}！", processDefinitionKey, e.getMessage());
            throw new BaseException("流程提交失败！");
        }
    }

    @Override
    public List<ProcessResultDTO> addProcessNextNodeTodo(ProcessTaskTodoDTO processTaskTodoDTO) {
        String instanceId = processTaskTodoDTO.getInstanceId();
        String businessId = processTaskTodoDTO.getBusinessId();
        Assertion.isBlank(instanceId, "流程实例id不能为空");
        Assertion.isBlank(businessId, "业务id不能为空");
        log.info("流程任务代办添加，流程实例Id:{}，业务Id:{}", instanceId, businessId);
        try {
            Map<String, ProcessResultDTO> todoMap = new HashMap<>();
            // 获取下一节点信息 （当前任务已完成，流程实例中的活跃任务为下一节点信息）
            List<ActTaskDTO> actTaskList = actTaskService.getCurrentTask(instanceId);
            if (CollectionUtils.isEmpty(actTaskList)) {
                log.info("流程业务代办添加，下一节点处理人为空！");
                return Collections.emptyList();
            }
            // 业务代办信息处理
            List<BusTodoDO> todoList = new ArrayList<>();
            for (ActTaskDTO actTaskDTO : actTaskList) {
                ProcessResultDTO processResultDTO = todoMap.get(actTaskDTO.getTaskKey());
                if (Objects.isNull(processResultDTO)) {
                    processResultDTO = new ProcessResultDTO();
                    processResultDTO.setDefinitionKey(actTaskDTO.getProcessDefinitionKey());
                    processResultDTO.setInstanceId(actTaskDTO.getProcessInstanceId());
                    processResultDTO.setTaskName(actTaskDTO.getTaskName());
                    processResultDTO.setTaskKey(actTaskDTO.getTaskKey());
                    todoMap.put(actTaskDTO.getTaskKey(), processResultDTO);
                }
                // 代办人添加代办
                List<String> todoUserList = actTaskDTO.getTodoUserList();
                if (CollectionUtils.isEmpty(todoUserList)) {
                    log.info("流程业务代办调价，任务id:{} 不存在代办人", actTaskDTO.getTaskId());
                    continue;
                }

                for (String todoUser : todoUserList) {
                    Long count = busTodoMapper.selectCount(new LambdaQueryWrapper<BusTodoDO>()
                            .eq(BusTodoDO::getTodoUserId, todoUser)
                            .eq(BusTodoDO::getTaskId, actTaskDTO.getTaskId())
                    );
                    if (count > Constants.ZERO) {
                        log.info("流程业务代办添加，代办人：{}，已存在任务id为：{}的代办！", todoUser, actTaskDTO.getTaskId());
                        continue;
                    }
                    processResultDTO.setTaskId(actTaskDTO.getTaskId());

                    // 代办信息
                    BusTodoDO busTodo = new BusTodoDO();
                    busTodo.setBusId(businessId);
                    busTodo.setInstanceId(instanceId);
                    busTodo.setTaskId(actTaskDTO.getTaskId());
                    busTodo.setName(actTaskDTO.getTaskName());
                    busTodo.setContent("任务代办节点：" + actTaskDTO.getTaskName());
                    busTodo.setBusinessTypeCode(actTaskDTO.getProcessDefinitionKey());
                    busTodo.setProcessNodeCode(actTaskDTO.getTaskKey());
                    busTodo.setIsView(Constants.ONE);
                    busTodo.setIsHandle(Constants.ONE);
                    busTodo.setNotifyTime(LocalDateTime.now());
                    busTodo.setTodoUserId(todoUser);
                    UserDO userDO = userMapper.selectById(todoUser);
                    busTodo.setTodoUserName(userDO.getUsername());
                    busTodo.setRemark(processTaskTodoDTO.getRemarks());
                    todoList.add(busTodo);
                }
            }
            // 代办事项不为空添加数据
            if (CollectionUtils.isNotEmpty(todoList)) {
                busTodoMapper.insertBatch(todoList);
            }
            return new ArrayList<>(todoMap.values());
        } catch (Exception e) {
            log.error("流程实例Id为:{} 的代办添加异常，错误信息为：{}！", instanceId, e.getMessage());
            throw new RuntimeException("流程实例启动失败，异常信息为:{}", e);
        }
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
