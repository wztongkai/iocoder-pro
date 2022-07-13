package com.iocoder.yudao.module.activiti.service;

import com.iocoder.yudao.module.activiti.dto.instance.ProcessInstanceDTO;
import com.iocoder.yudao.module.activiti.dto.ProcessResultDTO;

/**
 * 工作流中间层接口
 *
 * @author wu kai
 * @since 2022/7/13
 */
public interface ProcessService {

    /**
     * 流程实例启动
     * @param processInstanceDTO 启动参数
     * @return 启动流程返回结果
     */
    ProcessResultDTO startInstance(ProcessInstanceDTO processInstanceDTO);
}
