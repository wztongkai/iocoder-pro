package com.iocoder.yudao.module.commons.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * @author wu kai
 * @since 2022/9/6
 */
@Component
public class JdbcUtils {
    @Value("${spring.datasource.dynamic.datasource.master.url}")
    private String url;


    public HashMap<String, String> getDBInfo() {
        String[] split = url.split(":");
        String host = String.format("%s:%s:%s", split[0], split[1], split[2]);
        String[] portSplit = split[3].split("/");
        String port = portSplit[0];

        String[] databaseSplit = portSplit[1].split("\\?");
        String dbName = databaseSplit[0];
        HashMap<String, String> result = new HashMap<>();
        result.put("url",url);
        result.put("host",host);
        result.put("port",port);
        result.put("dbName",dbName);

        return result;
    }
}
