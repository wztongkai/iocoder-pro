package com.iocoder.yudao.module.activiti.service.impl;

import com.iocoder.yudao.module.activiti.service.BpmModelService;
import com.iocoder.yudao.module.activiti.service.BpmProcessDefinitionService;
import com.iocoder.yudao.module.activiti.vo.definition.BpmProcessDefinitionCreateReqVO;
import com.iocoder.yudao.module.activiti.vo.model.BpmModelCreateReqVO;
import com.iocoder.yudao.module.activiti.vo.model.BpmModelRespVO;
import com.iocoder.yudao.module.commons.utils.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import static com.iocoder.yudao.module.commons.constant.ErrorCodeConstants.*;
import static com.iocoder.yudao.module.commons.exception.ServiceExceptionUtil.exception;

/**
 * 流程定义实现
 *
 * @author wu kai
 * @since 2022-07-11
 */
@Service
@Slf4j
public class BpmModelServiceImpl implements BpmModelService {

    @Resource
    RepositoryService repositoryService;

    @Resource
    BpmProcessDefinitionService bpmProcessDefinitionService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String createModel(BpmModelCreateReqVO createReqVO, String bpmnXml) {
        // 校验流程 key 是否唯一
        Model modelKey = this.checkModelKeyUnique(createReqVO.getKey());
        if (modelKey != null) {
            throw exception(MODEL_KEY_EXISTS, createReqVO.getKey());
        }
        // 创建流程模型
        Model model = repositoryService.newModel();
        BeanUtil.copyProperties(createReqVO, model);
        // 保存流程模型
        repositoryService.saveModel(model);
        return model.getId();
    }

    @Override
    public BpmModelRespVO getModel(String id) {
        // 根据模型编号查询流程模型
        Model model = repositoryService.getModel(id);
        if (ObjectUtils.isEmpty(model)) {
            return null;
        }
        BpmModelRespVO bpmModelRespVO = new BpmModelRespVO();
        BeanUtil.copyProperties(model, bpmModelRespVO);
        return bpmModelRespVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deployModel(String id) {
        // 校验流程模型是否存在
        Model model = repositoryService.getModel(id);
        if (ObjectUtils.isEmpty(model)) {
            throw exception(MODEL_NOT_EXISTS);
        }
        // 获取流程图byte数组
        byte[] bpmnBytes = repositoryService.getModelEditorSource(model.getId());
        if (bpmnBytes == null) {
            throw exception(MODEL_IMAGE_NOT_EXISTS);
        }
        BpmProcessDefinitionCreateReqVO definitionCreateReqVO = new BpmProcessDefinitionCreateReqVO();
        BeanUtil.copyProperties(model, definitionCreateReqVO);
        definitionCreateReqVO.setBpmnBytes(bpmnBytes);
        // 创建流程定义
        bpmProcessDefinitionService.createProcessDefinition(definitionCreateReqVO);
    }

    /**
     * 校验流程 key 是否唯一 并返回流程信息
     *
     * @param modelKey 流程key
     * @return 流程信息
     */
    private Model checkModelKeyUnique(String modelKey) {
        return repositoryService.createModelQuery().modelKey(modelKey).singleResult();
    }
}
