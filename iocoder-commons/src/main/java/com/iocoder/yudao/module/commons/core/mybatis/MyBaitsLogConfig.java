package com.iocoder.yudao.module.commons.core.mybatis;

import org.apache.ibatis.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * mybatis-plus自定义日志输出
 */
public class MyBaitsLogConfig implements Log {

    private final Logger log;

    public MyBaitsLogConfig(String clazz) {
        log = LoggerFactory.getLogger(clazz);
    }

    @Override
    public boolean isDebugEnabled() {
        return true;
    }

    @Override
    public boolean isTraceEnabled() {
        return true;
    }

    @Override
    public void error(String s, Throwable e) {
        log.error(s,e);
    }

    @Override
    public void error(String s) {
        log.error(s);
    }

    @Override
    public void debug(String s) {
        log.info(s);
    }

    @Override
    public void trace(String s) {
        log.info(s);
    }

    @Override
    public void warn(String s) {
        log.info(s);
    }
}
