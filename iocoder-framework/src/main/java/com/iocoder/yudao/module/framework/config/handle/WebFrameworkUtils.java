package com.iocoder.yudao.module.framework.config.handle;

import com.iocoder.yudao.module.commons.core.domain.CommonResult;
import org.springframework.boot.autoconfigure.web.WebProperties;

import javax.servlet.ServletRequest;

/**
 * 专属于 web 包的工具类
 *
 * @author 芋道源码
 */
public class WebFrameworkUtils {


    private static final String REQUEST_ATTRIBUTE_COMMON_RESULT = "common_result";

    private static WebProperties properties;

    public WebFrameworkUtils(WebProperties webProperties) {
        WebFrameworkUtils.properties = webProperties;
    }


    public static void setCommonResult(ServletRequest request, CommonResult<?> result) {
        request.setAttribute(REQUEST_ATTRIBUTE_COMMON_RESULT, result);
    }


}
