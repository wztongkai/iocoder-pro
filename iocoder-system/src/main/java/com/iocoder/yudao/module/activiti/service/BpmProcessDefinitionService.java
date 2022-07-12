package com.iocoder.yudao.module.activiti.service;

import com.iocoder.yudao.module.activiti.vo.definition.BpmProcessDefinitionCreateReqVO;
import org.activiti.engine.repository.ProcessDefinition;

import javax.validation.Valid;

/**
 * 流程定义
 *
 * @author wu kai
 * @since 2022/7/11
 */
public interface BpmProcessDefinitionService {

    /**
     * 创建流程定义
     *
     * @param definitionCreateReqVO 流程模型信息
     */
    String createProcessDefinition(@Valid BpmProcessDefinitionCreateReqVO definitionCreateReqVO);

    /**
     * 获得 deploymentId 对应的 ProcessDefinition
     *
     * @param deploymentId 部署编号
     * @return 流程定义
     */
    ProcessDefinition getProcessDefinitionByDeploymentId(String deploymentId);

    /**
     * 获得编号对应的 ProcessDefinition
     *
     * @param id 编号
     * @return 流程定义
     */
    ProcessDefinition getProcessDefinition(String id);

    /**
     * 更新流程定义状态
     *
     * @param id 流程定义的编号
     * @param state 状态
     */
    void updateProcessDefinitionState(String id, Integer state);
}
