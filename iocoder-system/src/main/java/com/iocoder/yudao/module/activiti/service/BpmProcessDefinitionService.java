package com.iocoder.yudao.module.activiti.service;

import com.iocoder.yudao.module.activiti.vo.definition.BpmProcessDefinitionCreateReqVO;

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
    void createProcessDefinition(@Valid BpmProcessDefinitionCreateReqVO definitionCreateReqVO);
}
