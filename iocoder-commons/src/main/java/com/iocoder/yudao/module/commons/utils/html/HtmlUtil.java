package com.iocoder.yudao.module.commons.utils.html;

import com.iocoder.yudao.module.commons.utils.RemoteTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

/**
 * HTML 相关工具类
 *
 * @author wu kai
 * @date 2022/7/6
 */
public class HtmlUtil {

    /**
     * 填充ftl模板
     *
     * @param dataMap  数据
     * @param ftlFileName 文件名称
     * @param ftlFilePath 文件目录
     * @return 填充后html代码
     */
    public static String fillFtlFile(Map<String, Object> dataMap, String ftlFileName, String ftlFilePath) {
        Configuration configuration = new Configuration();
        configuration.setDefaultEncoding("UTF-8");
        RemoteTemplateLoader remoteTemplateLoader = new RemoteTemplateLoader(ftlFilePath);
        configuration.setTemplateLoader(remoteTemplateLoader);
        try {
            Template template = configuration.getTemplate(ftlFileName, "UTF-8");

            Writer out = new StringWriter();
            template.process(dataMap, out);
            out.flush();
            return out.toString()
                    .replace("\r\n", "")
                    .replace("\t", "");
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
        return null;
    }
}
