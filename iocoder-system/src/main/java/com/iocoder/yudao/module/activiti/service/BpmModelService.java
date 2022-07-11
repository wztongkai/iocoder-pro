package com.iocoder.yudao.module.activiti.service;


import com.iocoder.yudao.module.activiti.vo.model.BpmModelCreateReqVO;
import com.iocoder.yudao.module.activiti.vo.model.BpmModelRespVO;

import javax.validation.Valid;

/**
 * 流程定义实现
 *
 * @author wu kai
 * @since 2022-07-11
 */
public interface BpmModelService {

    /**
     * 创建流程模型
     *
     * @param createReqVO 创建信息
     * @return 创建的流程模型的编号
     */
    String createModel(@Valid BpmModelCreateReqVO createReqVO, String bpmnXml);

    /**
     * 获得流程模型
     *
     * @param id 编号
     * @return 流程模型
     */
    BpmModelRespVO getModel(String id);

    /**
     * 部署流程模型
     *
     * @param id 流程模型编号
     * @return
     */
    void deployModel(String id);
}
