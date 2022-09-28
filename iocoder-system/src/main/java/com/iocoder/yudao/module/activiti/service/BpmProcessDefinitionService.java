package com.iocoder.yudao.module.activiti.service;

import com.iocoder.yudao.module.activiti.vo.definition.BpmProcessDefinitionCreateReqVO;
import com.iocoder.yudao.module.activiti.vo.definition.BpmProcessDefinitionListReqVO;
import com.iocoder.yudao.module.activiti.vo.definition.BpmProcessDefinitionRespVO;
import org.activiti.engine.repository.ProcessDefinition;

import javax.validation.Valid;
import java.util.List;

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
     * 根据流程定义 key 获取已激活的流程定义
     *
     * @param key 流程定义 key
     * @return 流程定义
     */
    ProcessDefinition getActiveProcessDefinition(String key);

    /**
     * 更新流程定义状态
     *
     * @param id    流程定义的编号
     * @param state 状态
     */
    void updateProcessDefinitionState(String id, Integer state);

    /**
     * 获取流程定义列表
     *
     * @param listReqVO 获取条件
     * @return 列表
     */
    List<BpmProcessDefinitionRespVO> getProcessDefinitionList(BpmProcessDefinitionListReqVO listReqVO);

    /**
     * 获取流程定义bpmn
     *
     * @param id 流程定义编号
     * @return bpmn
     */
    String getProcessDefinitionBpmnXML(String id);
}
