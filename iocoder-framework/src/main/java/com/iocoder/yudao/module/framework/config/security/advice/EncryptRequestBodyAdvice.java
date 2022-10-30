package com.iocoder.yudao.module.framework.config.security.advice;

import com.iocoder.yudao.module.commons.config.iocoderConfig.IocoderConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Objects;

/**
 * 解密
 *
 * @author wu kai
 * @since 2022/10/26
 */

@Slf4j
@ControllerAdvice
public class EncryptRequestBodyAdvice implements RequestBodyAdvice {

    private static final ThreadLocal<Boolean> decryptLocal = new ThreadLocal<>();

    @Resource
    IocoderConfig iocoderConfig;
    private final static String[] NEED_RELEASE_METHOD = {"login"};

    @Override
    public boolean supports(MethodParameter methodParameter,@Nonnull Type targetType, @Nonnull Class<? extends HttpMessageConverter<?>> converterType) {
        String methodName = Objects.requireNonNull(methodParameter.getMethod()).getName();
        if (iocoderConfig.getIsEncrypt() && !Arrays.asList(NEED_RELEASE_METHOD).contains(methodName)) {
            decryptLocal.set(Boolean.TRUE);
            return true;
        }
        return false;
    }

    @Override
    @Nonnull
    public HttpInputMessage beforeBodyRead(@Nonnull HttpInputMessage inputMessage, @Nonnull MethodParameter parameter, @Nonnull Type targetType,@Nonnull Class<? extends HttpMessageConverter<?>> converterType) {
        Boolean isEncrypt = decryptLocal.get();
        if (Objects.nonNull(isEncrypt) && isEncrypt) {
            decryptLocal.remove();
            // 解密，并重新设置请求体
            return new DecryptHttpInputMessage(inputMessage);
        }
        return inputMessage;
    }

    @Override
    @Nonnull
    public Object afterBodyRead(@Nonnull Object body, @Nonnull HttpInputMessage inputMessage, @Nonnull MethodParameter parameter,@Nonnull Type targetType,@Nonnull Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    @Override
    public Object handleEmptyBody(Object body, @Nonnull HttpInputMessage inputMessage,@Nonnull MethodParameter parameter, @Nonnull Type targetType, @Nonnull Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }
}
