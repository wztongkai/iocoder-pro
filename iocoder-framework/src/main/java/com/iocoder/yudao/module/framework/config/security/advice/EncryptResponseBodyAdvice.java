package com.iocoder.yudao.module.framework.config.security.advice;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iocoder.yudao.module.commons.config.iocoderConfig.IocoderConfig;
import com.iocoder.yudao.module.commons.utils.AESUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 加密
 *
 * @author wu kai
 * @since 2022/10/26
 */
@Slf4j
@ControllerAdvice
public class EncryptResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    private static final ThreadLocal<Boolean> encryptLocal = new ThreadLocal<>();

    @Resource
    IocoderConfig iocoderConfig;
    // 不需要加密的接口
    private final static String[] NEED_RELEASE_METHOD = {"getCaptchaImage","swaggerResources","getDocumentation"};

    @Override
    public boolean supports(MethodParameter returnType, @Nonnull Class<? extends HttpMessageConverter<?>> converterType) {
        String methodName = Objects.requireNonNull(returnType.getMethod()).getName();

        if (iocoderConfig.getIsEncrypt() && !Arrays.asList(NEED_RELEASE_METHOD).contains(methodName)) {
            encryptLocal.set(Boolean.TRUE);
            return true;
        }
        return false;
    }

    @Override
    public Object beforeBodyWrite(Object body, @Nonnull MethodParameter returnType, @Nonnull MediaType selectedContentType,
                                  @Nonnull Class<? extends HttpMessageConverter<?>> selectedConverterType, @Nonnull ServerHttpRequest request, @Nonnull ServerHttpResponse response) {
        Boolean isEncrypt = encryptLocal.get();
        if (Objects.nonNull(isEncrypt) && !isEncrypt) {
            encryptLocal.remove();
            return body;
        }
        String requestStr = JSONObject.toJSONStringWithDateFormat(body,"yyyy-MM-dd HH:mm:ss");
        Object requestObj = JSONObject.parse(requestStr);
        Map<String, String> rMap = new HashMap<>();
        rMap.put("requestStr", requestStr);
        jsonLoopRequest(rMap, requestObj);
        String content = rMap.get("requestStr");
        return AESUtils.encrypt(content);
    }

    public void jsonLoopRequest(Map<String, String> rMap, Object object) {
        if (object instanceof JSONObject) {
            JSONObject jsonObject = (JSONObject) object;
            for (Map.Entry<String, Object> stringObjectEntry : jsonObject.entrySet()) {
                Object o = (stringObjectEntry).getValue();
                if (o instanceof Long) {
                    replaceRequest(rMap, (stringObjectEntry).getValue().toString());
                } else if (o instanceof JSONArray) {
                    jsonLoopRequest(rMap, o);
                } else if (o instanceof JSONObject) {
                    jsonLoopRequest(rMap, o);
                }
            }
        }
        if (object instanceof JSONArray) {
            JSONArray jsonArray = (JSONArray) object;
            for (Object o : jsonArray) {
                jsonLoopRequest(rMap, o);
            }
        }
    }

    public void replaceRequest(Map<String, String> rMap, String str) {
        String requestStr = rMap.get("requestStr");
        if (requestStr.charAt(requestStr.indexOf(str) - 1) != '\"' && requestStr.charAt(requestStr.indexOf(str) - 1) != ',') {
            String rst = requestStr.replaceFirst(str, "\"" + str + "\"");
            rMap.put("requestStr", rst);
        }
    }
}
