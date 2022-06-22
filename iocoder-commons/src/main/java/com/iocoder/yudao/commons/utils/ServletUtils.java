package com.iocoder.yudao.commons.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author wu kai
 * @date 2022/6/22
 */
public class ServletUtils {

    /**
     * 将字符串渲染到客户端
     *
     * @param response 渲染对象
     * @param string   待渲染的字符串
     */
    public static void renderString(HttpServletResponse response, String string) {
        try {
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(string);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Long getLoginUserId(){
        return null;
    }
}
