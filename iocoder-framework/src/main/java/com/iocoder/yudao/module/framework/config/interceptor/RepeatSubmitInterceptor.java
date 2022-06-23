package com.iocoder.yudao.module.framework.config.interceptor;

import com.alibaba.fastjson2.JSON;
import com.iocoder.yudao.module.commons.annotation.RepeatSubmit;
import com.iocoder.yudao.module.commons.core.domain.CommonResult;
import com.iocoder.yudao.module.commons.utils.ServletUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

import static com.iocoder.yudao.module.commons.constant.GlobalErrorCodeConstants.REPEATED_REQUESTS;

/**
 * 防止重复提交拦截器
 *
 * @author kai wu
 */
@Component
public abstract class RepeatSubmitInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            RepeatSubmit annotation = method.getAnnotation(RepeatSubmit.class);
            if (annotation != null) {
                if (this.isRepeatSubmit(request, annotation)) {
                    CommonResult<String> commonResult = CommonResult.error(REPEATED_REQUESTS);
                    ServletUtils.renderString(response, JSON.toJSONString(commonResult));
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 验证是否重复提交由子类实现具体的防重复提交的规则
     *
     * @param request
     * @return
     * @throws Exception
     */
    public abstract boolean isRepeatSubmit(HttpServletRequest request, RepeatSubmit annotation);
}
