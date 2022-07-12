package com.iocoder.yudao.module.activiti.service.impl;

import cn.hutool.core.util.StrUtil;
import com.iocoder.yudao.module.activiti.service.BpmProcessDefinitionService;
import com.iocoder.yudao.module.activiti.vo.definition.BpmProcessDefinitionCreateReqVO;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.persistence.entity.SuspensionState;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * 流程定义实现类
 *
 * @author wu kai
 * @since 2022/7/11
 */
@Service
@Slf4j
public class BpmProcessDefinitionServiceImpl implements BpmProcessDefinitionService {

    // 流程图文件后缀
    private static final String BPMN_FILE_SUFFIX = ".bpmn";

    @Resource
    RepositoryService repositoryService;

    @Override
    public String createProcessDefinition(BpmProcessDefinitionCreateReqVO definitionCreateReqVO) {

        Deployment deploy = repositoryService.createDeployment()
                .key(definitionCreateReqVO.getKey()).name(definitionCreateReqVO.getName())
                .addBytes(definitionCreateReqVO.getKey() + BPMN_FILE_SUFFIX, definitionCreateReqVO.getBpmnBytes())
                .deploy();

        ProcessDefinition definition = repositoryService.createProcessDefinitionQuery().deploymentId(deploy.getId()).singleResult();
        return definition.getId();
    }

    @Override
    public ProcessDefinition getProcessDefinitionByDeploymentId(String deploymentId) {
        if (StrUtil.isEmpty(deploymentId)) {
            return null;
        }
        return repositoryService.createProcessDefinitionQuery().deploymentId(deploymentId).singleResult();
    }

    @Override
    public ProcessDefinition getProcessDefinition(String id) {
        return repositoryService.getProcessDefinition(id);
    }

    @Override
    public void updateProcessDefinitionState(String id, Integer state) {
        // 激活
        if (Objects.equals(SuspensionState.ACTIVE.getStateCode(), state)) {
            repositoryService.activateProcessDefinitionById(id, false, null);
            return;
        }
        // 挂起
        if (Objects.equals(SuspensionState.SUSPENDED.getStateCode(), state)) {
            repositoryService.suspendProcessDefinitionById(id, false, null);
            return;
        }
        log.error("[updateProcessDefinitionState][流程定义({}) 修改状态({})]", id, state);
    }
}
