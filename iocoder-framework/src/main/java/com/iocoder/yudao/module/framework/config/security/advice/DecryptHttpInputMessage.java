package com.iocoder.yudao.module.framework.config.security.advice;

import com.iocoder.yudao.module.commons.utils.AESUtils;
import com.iocoder.yudao.module.commons.utils.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

/**
 * 请求参数解密，并重新设置请求体
 *
 * @author wu kai
 * @since 2022/10/26
 */
public class DecryptHttpInputMessage implements HttpInputMessage {

    private final HttpHeaders headers;
    private InputStream body;

    public DecryptHttpInputMessage(HttpInputMessage inputMessage) {
        // 获取请求头
        this.headers = inputMessage.getHeaders();
        try {
            // 获取加密串
            String context = new BufferedReader(new InputStreamReader(inputMessage.getBody())).lines().collect(Collectors.joining(System.lineSeparator()));
            if (StringUtils.isNotEmpty(context)) {
                // 解密
                String decrypt = AESUtils.decrypt(context);
                if (StringUtils.isNotEmpty(decrypt)) {
                    // 设置请求体
                    this.body = new ByteArrayInputStream(decrypt.getBytes(StandardCharsets.UTF_8));
                } else {
                    this.body = new ByteArrayInputStream("{}".getBytes(StandardCharsets.UTF_8));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public InputStream getBody() {
        return body;
    }

    @Override
    public HttpHeaders getHeaders() {
        return headers;
    }
}
