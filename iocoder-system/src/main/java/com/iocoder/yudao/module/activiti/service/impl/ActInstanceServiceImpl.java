package com.iocoder.yudao.module.activiti.service.impl;

import com.iocoder.yudao.module.activiti.dto.instance.ActProcessInstanceDTO;
import com.iocoder.yudao.module.activiti.service.ActInstanceService;
import com.iocoder.yudao.module.commons.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

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

    @Override
    public ProcessInstance startProcessInsByDefId(String processDefinitionId, String businessKey, Map<String, Object> variables) {

        try {
            return runtimeService.startProcessInstanceById(processDefinitionId, businessKey, variables);
        } catch (Exception e) {
            log.error("流程实例启动失败，异常信息为:{}", e.getMessage());
            throw new ServiceException("流程实例启动失败，异常信息为:{}", e.getMessage());
        }
    }

    @Override
    public ActProcessInstanceDTO getProcessInstanceById(String processInstanceId) {
        ActProcessInstanceDTO dto = new ActProcessInstanceDTO();
        // 校验流程是否完成
        boolean processFinished = isProcessFinished(processInstanceId);
        if(processFinished){
            // 获取历史流程实例
            HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
            dto = ActProcessInstanceDTO.builder()
                    .instanceId(historicProcessInstance.getId())
                    .processDefinitionId(historicProcessInstance.getProcessDefinitionId())
                    .processDefinitionKey(historicProcessInstance.getProcessDefinitionKey())
                    .build();
        }else{
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
        if(ObjectUtils.isEmpty(processInstance)){
            // 查询历史流程实例
            HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(instanceId).singleResult();
            if(ObjectUtils.isNotEmpty(historicProcessInstance)){
                return true;
            }
            return false;
        }
        return false;
    }
}
