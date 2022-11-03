package com.iocoder.yudao.module.file.infra.service.impl;

import com.iocoder.yudao.module.commons.config.Assertion;
import com.iocoder.yudao.module.commons.config.iocoderConfig.IocoderConfig;
import com.iocoder.yudao.module.commons.constant.Constants;
import com.iocoder.yudao.module.commons.exception.base.BaseException;
import com.iocoder.yudao.module.commons.utils.file.FileUploadUtils;
import com.iocoder.yudao.module.file.infra.service.SysPluginsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Objects;

/**
 * @author wu kai
 * @since 2022/11/3
 */
@Service
@Slf4j
public class SysPluginsServiceImpl implements SysPluginsService {

    @Resource
    IocoderConfig iocoderConfig;

    @Value("${resourceServer.url}")
    private String RESOURCE_SERVER_URL;

    @Override
    public void JGPluginsDownload(Integer pluginsType, HttpServletResponse response) {
        Assertion.isIntBlank(pluginsType, "插件类型不能为空");
        String pluginPath;
        if (Objects.equals(pluginsType, Constants.ONE)) {
            // 获取在线编辑插件地址
            pluginPath = RESOURCE_SERVER_URL + iocoderConfig.getOnlinePlugin();
        } else {
            // 获取签章插件地址
            pluginPath = RESOURCE_SERVER_URL + iocoderConfig.getSingPlugin();
        }
        String fileName = pluginPath.substring(pluginPath.lastIndexOf("/") + 1, pluginPath.lastIndexOf(".") - 1)+".zip";
        try {
            log.info("插件下载开始，插件地址为：{}", pluginPath);
            InputStream inputStream = FileUploadUtils.getInputStream(pluginPath);
            byte[] pluginBytes = IOUtils.toByteArray(inputStream);
            this.downLoadPlugin(response, pluginBytes, fileName);
        } catch (Exception e) {
            log.error("插件下载失败，下载失败的插件地址：{}，错误信息为：{}", pluginPath, e.getMessage());
            throw new BaseException("插件下载失败，错误信息为：{}", e.getMessage());
        }
    }

    private void downLoadPlugin(HttpServletResponse response, byte[] pluginBytes, String fileName) throws IOException {
        // 输出响应流
        OutputStream out = response.getOutputStream();
        // 设置响应头信息
        response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        response.setContentType("application/octet-stream; charset=UTF-8");
        // 输出并关闭
        out.write(pluginBytes);
        out.flush();
        out.close();
    }
}
