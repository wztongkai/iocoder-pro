package com.iocoder.yudao.module.weboffice.service;

import com.iocoder.yudao.module.weboffice.bo.OnlineGenerateBaseBO;

/**
 * @author wu kai
 * @since 2022/10/13
 */
public interface WebOfficeOnlineService {
    /**
     * 在线生成用户文档
     * @return 文档信息
     */
    OnlineGenerateBaseBO onlineGenerateUserDoc();
}
