package com.iocoder.yudao.module.commons.utils;

import freemarker.cache.URLTemplateLoader;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * 远程URL地址的方式实现模板加载
 *
 * @author wukai
 */
public class RemoteTemplateLoader extends URLTemplateLoader {
    /**
     * 远程模板文件的存储路径（目录）
     */
    private String remotePath;

    public RemoteTemplateLoader(String remotePath) {
        if (remotePath == null) {
            throw new IllegalArgumentException("remotePath is null");
        }
        this.remotePath = canonicalizePrefix(remotePath);
        if (this.remotePath.indexOf('/') == 0) {
            this.remotePath = this.remotePath.substring(this.remotePath.indexOf('/') + 1);
        }
    }

    @Override
    protected URL getURL(String name) {
        String fullPath = this.remotePath + name;
        fullPath = fullPath.replace("_zh", "")
                .replace("_CN", "")
                .replace("_en", "")
                .replace("_EN", "")
                .replace("_cn", "");
        if ((this.remotePath.equals("/")) && (!isSchemeLess(fullPath))) {
            return null;
        }
        URL url = null;
        try {
            url = new URL(fullPath);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        System.out.println("url===" + url);
        return url;
    }

    private static boolean isSchemeLess(String fullPath) {
        int i = 0;
        int ln = fullPath.length();

        if ((i < ln) && (fullPath.charAt(i) == '/')) {
            i++;
        }

        while (i < ln) {
            char c = fullPath.charAt(i);
            if (c == '/') {
                return true;
            }
            if (c == ':') {
                return false;
            }
            i++;
        }
        return true;
    }

}
