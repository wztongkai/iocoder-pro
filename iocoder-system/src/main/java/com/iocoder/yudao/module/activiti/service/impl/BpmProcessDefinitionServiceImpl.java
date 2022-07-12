package com.iocoder.yudao.module.activiti.service.impl;

import cn.hutool.core.util.StrUtil;
import com.iocoder.yudao.module.activiti.service.BpmProcessDefinitionService;
import com.iocoder.yudao.module.activiti.vo.definition.BpmProcessDefinitionCreateReqVO;
import com.iocoder.yudao.module.activiti.vo.definition.BpmProcessDefinitionListReqVO;
import com.iocoder.yudao.module.activiti.vo.definition.BpmProcessDefinitionRespVO;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.persistence.entity.SuspensionState;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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

    @Override
    public List<BpmProcessDefinitionRespVO> getProcessDefinitionList(BpmProcessDefinitionListReqVO listReqVO) {
        // 查询流程定义列表
        List<ProcessDefinition> processDefinitionList = repositoryService.createProcessDefinitionQuery()
                .orderByProcessDefinitionKey().desc()
                .list();
        if (CollectionUtils.isEmpty(processDefinitionList)) {
            return Collections.emptyList();
        }
        List<BpmProcessDefinitionRespVO> respVOArrayList = new ArrayList<>();
        processDefinitionList.forEach(processDefinition -> {
            BpmProcessDefinitionRespVO respVO = BpmProcessDefinitionRespVO.builder()
                    .id(processDefinition.getId())
                    .name(processDefinition.getName())
                    .key(processDefinition.getKey())
                    .version(processDefinition.getVersion())
                    .description(processDefinition.getDescription())
                    .resourceName(processDefinition.getResourceName())
                    .deploymentId(processDefinition.getDeploymentId())
                    .build();
            respVOArrayList.add(respVO);
        });
        return respVOArrayList;
    }

    @Override
    public String getProcessDefinitionBpmnXML(String id) {
        BpmnModel bpmnModel = repositoryService.getBpmnModel(id);
        if(ObjectUtils.isEmpty(bpmnModel)){
            return null;
        }
        return StrUtil.utf8Str(getBpmnBytes(bpmnModel));
    }

    private byte[] getBpmnBytes(BpmnModel bpmnModel) {
        if (bpmnModel == null) {
            return new byte[0];
        }
        BpmnXMLConverter converter = new BpmnXMLConverter();
        return converter.convertToXML(bpmnModel);
    }
}
