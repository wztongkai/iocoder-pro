package com.iocoder.yudao.module.activiti.service.impl;

import com.iocoder.yudao.module.activiti.service.BpmProcessDefinitionService;
import com.iocoder.yudao.module.activiti.vo.definition.BpmProcessDefinitionCreateReqVO;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;

import javax.annotation.Resource;

/**
 * 流程定义实现类
 *
 * @author wu kai
 * @since 2022/7/11
 */
public class BpmProcessDefinitionServiceImpl implements BpmProcessDefinitionService {

    // 流程图文件后缀
    private static final String BPMN_FILE_SUFFIX = ".bpmn";

    @Resource
    RepositoryService repositoryService;

    @Override
    public void createProcessDefinition(BpmProcessDefinitionCreateReqVO definitionCreateReqVO) {

        repositoryService.createDeployment()
                .key(definitionCreateReqVO.getKey()).name(definitionCreateReqVO.getName())
                .addBytes(definitionCreateReqVO.getKey() + BPMN_FILE_SUFFIX, definitionCreateReqVO.getBpmnBytes());

    }
}
