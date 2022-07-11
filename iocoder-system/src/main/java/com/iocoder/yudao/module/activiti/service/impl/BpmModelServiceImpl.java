package com.iocoder.yudao.module.activiti.service.impl;

import com.iocoder.yudao.module.activiti.service.BpmModelService;
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

import static com.iocoder.yudao.module.commons.constant.ErrorCodeConstants.MODEL_KEY_EXISTS;
import static com.iocoder.yudao.module.commons.exception.ServiceExceptionUtil.exception;

/**
 *流程定义实现
 *
 * @author wu kai
 * @since 2022-07-11
 */
@Service
@Slf4j
public class BpmModelServiceImpl implements BpmModelService {

    @Resource
    RepositoryService repositoryService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String createModel(BpmModelCreateReqVO createReqVO) {
        // 校验流程 key 是否唯一
        Model modelKey = this.checkModelKeyUnique(createReqVO.getKey());
        if (modelKey != null) {
            throw exception(MODEL_KEY_EXISTS, createReqVO.getKey());
        }
        // 创建流程模型
        Model model = repositoryService.newModel();
        BeanUtil.copyProperties(createReqVO,model);
        // 保存流程模型
        repositoryService.saveModel(model);
        return model.getId();
    }

    @Override
    public BpmModelRespVO getModel(String id) {
        // 根据模型编号查询流程模型
        Model model = repositoryService.getModel(id);
        if(ObjectUtils.isEmpty(model)){
            return null;
        }
        BpmModelRespVO bpmModelRespVO = new BpmModelRespVO();
        BeanUtil.copyProperties(model,bpmModelRespVO);
        return bpmModelRespVO;
    }

    /**
     * 校验流程 key 是否唯一 并返回流程信息
     * @param modelKey 流程key
     * @return 流程信息
     */
    private Model checkModelKeyUnique(String modelKey) {
        return repositoryService.createModelQuery().modelKey(modelKey).singleResult();
    }
}
