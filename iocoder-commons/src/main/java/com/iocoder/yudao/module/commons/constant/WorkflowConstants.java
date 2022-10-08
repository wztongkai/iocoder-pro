package com.iocoder.yudao.module.commons.constant;

/**
 * 工作流通用常量
 *
 * @author wu kai
 * @since 2022/10/8
 */
public class WorkflowConstants {

    public static class BaseConstants {

        // 流程状态(0：待启动；1：运行中；2：已挂起；3：已取消；99：已结束)
        public static final Integer PROCESS_STATUS_0 = 0;
        public static final Integer PROCESS_STATUS_1 = 1;
        public static final Integer PROCESS_STATUS_2 = 2;
        public static final Integer PROCESS_STATUS_3 = 3;
        public static final Integer PROCESS_STATUS_99 = 99;

        public static final String FLOW_START = "FLOW_START";
        public static final String FLOW_END = "FLOW_END";
        /**
         * 挂起
         */
        public static final String PROCESS_SUSPEND = "suspend";
        /**
         * 激活
         */
        public static final String PROCESS_ACTIVATE = "activate";
        /**
         * 取消
         */
        public static final String PROCESS_CANCEL = "cancel";
    }
}
