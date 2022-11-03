package com.iocoder.yudao.module.file.infra.service;

import javax.servlet.http.HttpServletResponse;

/**
 * @author wu kai
 * @since 2022/11/3
 */
public interface SysPluginsService {

    void JGPluginsDownload(Integer pluginsType, HttpServletResponse response);
}
