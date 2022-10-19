package com.iocoder.yudao.module.weboffice.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wu kai
 * @since 2022/10/13
 */
public interface WebOfficeBasicService {
    /**
     * 在线编辑，打开文件
     *
     * @param fileUrl  文件链接
     * @param response response
     * @return 打开结果（成功/失败）
     */
    Boolean onlineOpenFile(String fileUrl, HttpServletResponse response);

    /**
     * 在线编辑，保存文件
     *
     * @param request 请求
     * @return 保存结果（成功/失败）
     */
    Boolean onlineSaveFile(HttpServletRequest request);

    /**
     * 在线编辑，保存文件为pdf
     * @param request 请求信息
     * @return 保存结果
     */
    Boolean onlineSaveFilePDF(HttpServletRequest request);
}
