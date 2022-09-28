package com.iocoder.yudao.module.activiti.service;

import com.iocoder.yudao.module.activiti.dto.ProcessResultDTO;
import com.iocoder.yudao.module.activiti.dto.instance.ProcessInstanceDTO;
import com.iocoder.yudao.module.activiti.dto.instance.ProcessSubmitDTO;
import com.iocoder.yudao.module.activiti.dto.task.ProcessTaskTodoDTO;

import java.util.List;

/**
 * 工作流中间层接口
 *
 * @author wu kai
 * @since 2022/7/13
 */
public interface ProcessService {

    /**
     * 流程实例启动 添加代办任务
     *
     * @param processInstanceDTO 启动参数
     * @return 启动流程返回结果
     */
    ProcessResultDTO startInstance(ProcessInstanceDTO processInstanceDTO);

    /**
     * 提交流程
     *
     * @param processSubmitDTO 提交流程参数
     * @return 提交结果
     */
    ProcessResultDTO submit(ProcessSubmitDTO processSubmitDTO);

    /**
     * 添加下一节点的处理人代办
     *
     * @param processTaskTodoDTO 流程任务代办参数
     * @return 下一节点的处理人代办
     */
    List<ProcessResultDTO> addProcessNextNodeTodo(ProcessTaskTodoDTO processTaskTodoDTO);


}
