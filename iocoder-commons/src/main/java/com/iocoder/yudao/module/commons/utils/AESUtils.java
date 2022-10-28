package com.iocoder.yudao.module.commons.utils;

import com.iocoder.yudao.module.commons.config.iocoderConfig.IocoderConfig;
import com.iocoder.yudao.module.commons.exception.base.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * 加、解密工具类
 *
 * @author wu kai
 * @since 2022/10/26
 */

@Slf4j
@Configuration
@Component
public class AESUtils {

    /**
     * 算法/加密模式/填充方式
     */
    private static final String AES_PKCS5P = "AES/ECB/PKCS5Padding";

    @Resource
    IocoderConfig iocoderConfig;

    private static String AesDataKey = "4%YkW!@g5LGcf9Ut";

    @PostConstruct
    public void getYmlParam() {
        AesDataKey = iocoderConfig.getAesDataKey();
    }

    public static String getAesDataKey() {
        return AesDataKey;
    }


    /**
     * AES加密
     *
     * @param context 需要加密的字符串
     * @return 加密后的字符串
     */
    public static String encrypt(String context) {
        return encrypt(context, getAesDataKey());
    }

    /**
     * AES解密
     *
     * @param context 需要解密的字符串
     * @return 解密后的字符串
     */
    public static String decrypt(String context) {
        return decrypt(context, getAesDataKey());
    }

    /**
     * AES加密
     *
     * @param context    需要加密的字符串
     * @param aesDataKey 加密秘钥
     * @return 加密后的字符串
     */
    public static String encrypt(String context, String aesDataKey) {
        log.info("AES加密开始，加密秘钥为：{}，需要加密的内容为：{}", aesDataKey, context);
        if (StringUtils.isEmpty(aesDataKey) || aesDataKey.length() != 16) {
            log.error("AES加密失败，加密秘钥为空或秘钥长度不正确（16位）");
            throw new RuntimeException("加密秘钥为空或不正确");
        }
        if (StringUtils.isEmpty(context)) {
            log.info("加密内容为空，加密结束");
            return null;
        }
        try {
            // 获取 加解密秘钥 和 操作内容的 byte
            byte[] keyBytes = aesDataKey.getBytes(StandardCharsets.UTF_8);
            byte[] contextBytes = context.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
            // "算法/模式/补码方式"
            Cipher cipher = Cipher.getInstance(AES_PKCS5P);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] encrypted = cipher.doFinal(contextBytes);
            // 使用BASE64做转码，相当于两次加密效果
            return new Base64().encodeToString(encrypted);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            log.error("AES加密失败，加密秘钥为：{}，加密失败的内容为：{}，错误信息为：{}", aesDataKey, context, e.getMessage());
            throw new BaseException("加密失败，加密数据为：{}", context);
        }
    }

    /**
     * AES解密
     *
     * @param context    需要解密的字符串
     * @param aesDataKey 解密秘钥
     * @return 解密成功后的字符串
     */
    public static String decrypt(String context, String aesDataKey) {
        log.info("AES解密开始，解密秘钥为：{}，需要解密的内容为：{}", aesDataKey, context);
        if (StringUtils.isEmpty(aesDataKey) || aesDataKey.length() != 16) {
            log.error("AES解密失败，解密秘钥为空或秘钥长度不正确（16位）");
            throw new RuntimeException("解密秘钥为空或不正确");
        }
        if (StringUtils.isEmpty(context)) {
            log.info("解密内容为空，解密结束");
            return null;
        }
        try {
            // 获取 加解密秘钥 byte
            byte[] keyBytes = aesDataKey.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
            // "算法/模式/补码方式"
            Cipher cipher = Cipher.getInstance(AES_PKCS5P);
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            // 先使用BASE64解密
            byte[] decrypted = new Base64().decode(context);
            // aes解密
            byte[] doFinal = cipher.doFinal(decrypted);
            return new String(doFinal, StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            log.error("AES解密失败，解密秘钥为：{}，解密失败的内容为：{}，错误信息为：{}", aesDataKey, context, e.getMessage());
            throw new BaseException("解密失败，解密数据为：{}", context);
        }
    }

    public static void main(String[] args) {
//        String encrypt = decrypt("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");
//        System.out.println("encrypt = " + encrypt);
//        System.out.println("test = " + test);
        String encrypt = encrypt("{\n" +
                "  \"code\": 200,\n" +
                "  \"data\": {\n" +
                "    \"username\": \"曹雨亭\",\n" +
                "    \"nickname\": \"caoyuting\",\n" +
                "    \"lastName\": \"Cao\",\n" +
                "    \"firstName\": \"YuTing\",\n" +
                "    \"nameCode\": \"2580 7183 0080\",\n" +
                "    \"birthProvince\": \"天津市\",\n" +
                "    \"birthday\": \"2022-10-19 00:00:00\",\n" +
                "    \"remark\": \"111\",\n" +
                "    \"email\": \"2580211254@qq.com\",\n" +
                "    \"mobile\": \"13121022996\",\n" +
                "    \"sex\": 1,\n" +
                "    \"avatar\": \"\",\n" +
                "    \"id\": \"1580861173744463874\",\n" +
                "    \"status\": 0,\n" +
                "    \"loginIp\": \"\",\n" +
                "    \"createTime\": \"2022-10-14 18:00:49\",\n" +
                "    \"deptList\": [\n" +
                "      {\n" +
                "        \"id\": \"100\",\n" +
                "        \"creator\": \"admin\",\n" +
                "        \"createTime\": \"2021-01-05 17:03:47\",\n" +
                "        \"updater\": \"103\",\n" +
                "        \"updateTime\": \"2022-06-25 23:19:03\",\n" +
                "        \"deleted\": false,\n" +
                "        \"name\": \"国家机械工业集团\",\n" +
                "        \"parentId\": \"0\",\n" +
                "        \"sort\": 0,\n" +
                "        \"leaderUserId\": \"1\",\n" +
                "        \"phone\": \"15888888888\",\n" +
                "        \"email\": \"ry@qq.com\",\n" +
                "        \"status\": 0,\n" +
                "        \"admin\": false\n" +
                "      },\n" +
                "      {\n" +
                "        \"id\": \"101\",\n" +
                "        \"creator\": \"admin\",\n" +
                "        \"createTime\": \"2021-01-05 17:03:47\",\n" +
                "        \"updater\": \"1\",\n" +
                "        \"updateTime\": \"2022-08-30 17:50:03\",\n" +
                "        \"deleted\": false,\n" +
                "        \"name\": \"北京总公司\",\n" +
                "        \"parentId\": \"100\",\n" +
                "        \"sort\": 1,\n" +
                "        \"leaderUserId\": \"1561606936288215042\",\n" +
                "        \"phone\": \"15888888888\",\n" +
                "        \"email\": \"ry@qq.com\",\n" +
                "        \"status\": 0,\n" +
                "        \"admin\": false\n" +
                "      }\n" +
                "    ],\n" +
                "    \"postList\": [\n" +
                "      {\n" +
                "        \"id\": \"1564817342540419073\",\n" +
                "        \"creator\": \"1\",\n" +
                "        \"createTime\": \"2022-08-31 11:28:22\",\n" +
                "        \"updater\": \"1\",\n" +
                "        \"updateTime\": \"2022-08-31 13:12:34\",\n" +
                "        \"deleted\": false,\n" +
                "        \"code\": \"PM\",\n" +
                "        \"name\": \"产品经理\",\n" +
                "        \"sort\": 2,\n" +
                "        \"status\": 0,\n" +
                "        \"remark\": \"产品经理\",\n" +
                "        \"admin\": false\n" +
                "      }\n" +
                "    ]\n" +
                "  },\n" +
                "  \"msg\": \"成功\"\n" +
                "}");
        System.out.println("encrypt = " + encrypt);
    }

}
