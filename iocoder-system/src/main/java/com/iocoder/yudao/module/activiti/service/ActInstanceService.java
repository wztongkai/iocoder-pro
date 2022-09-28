package com.iocoder.yudao.module.activiti.service;

import com.iocoder.yudao.module.activiti.dto.instance.ActProcessInstanceDTO;
import org.activiti.engine.runtime.ProcessInstance;

import java.util.Map;

/**
 * 流程实例接口
 *
 * @author wu kai
 * @since 2022/7/13
 */
public interface ActInstanceService {

    /**
     * 流程实例启动
     *
     * @param processDefinitionId 流程定义 id
     * @param businessKey         业务流程 key
     * @param variables           流程数据 map
     * @return 流程实例对象
     */
    ProcessInstance startProcessInsByDefId(String processDefinitionId, String businessKey, Map<String, Object> variables);

    /**
     * 根据流程实例id获取流程实例信息
     *
     * @param processInstanceId 流程实例id
     * @return 流程实例信息
     */
    ActProcessInstanceDTO getProcessInstanceById(String processInstanceId);

    /**
     * 根据流程实例id 查看流程是否已完成
     *
     * @param instanceId 流程实例id
     * @return 结果
     */
    boolean isProcessFinished(String instanceId);

    /**
     * 当前流程是否处于挂起状态
     *
     * @param taskId 任务ID
     * @return 当前流程是否处于挂起状态
     */
    boolean isSuspendInstanceByTaskId(String taskId);
}
