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
        String encrypt = decrypt("41Iv7ZjXnOF64v/CH8Q+MZ5T11X6uO6qvKvYOIPc5lUlQSp9yINAUXSIswPiBJP0ziTTv4OGGOVSp+ZAyuxxMD57LiUFGEkuvcLVeNJ6WzTuxhZsgZGjbYaTKNoRwDGD7sw3AoKl6LZc7qWFDoZRdoGObbO0pVuldkkE3NsRyyVKwEqiQpQsoNqErle4XcUzZ92A98Uu3tvxSvqfbTCBmnSh1W1P/zct2jt8rS+0J2i94qeTjomMH/5iX5HCtaGI8pdm8lcfEymFxhULwxM07E0sOFS0S5ULQHnP/qg/yu+6//rGJOzhDBsz4Gw50AINwPniZB9AdjswXsPb+PkykFLx/spIAft0mYJUmUdwO8P65eVP0xEal3eDbOKZ9kekdv418usmt95sKT8tvpuMtw==");
//        System.out.println("encrypt = " + encrypt);
//        System.out.println("test = " + test);
//        String encrypt = encrypt("{\n" +
//                "  \"code\": 200,\n" +
//                "  \"data\": {\n" +
//                "    \"username\": \"曹雨亭\",\n" +
//                "    \"nickname\": \"caoyuting\",\n" +
//                "    \"lastName\": \"Cao\",\n" +
//                "    \"firstName\": \"YuTing\",\n" +
//                "    \"nameCode\": \"2580 7183 0080\",\n" +
//                "    \"birthProvince\": \"天津市\",\n" +
//                "    \"birthday\": \"2022-10-19 00:00:00\",\n" +
//                "    \"remark\": \"111\",\n" +
//                "    \"email\": \"2580211254@qq.com\",\n" +
//                "    \"mobile\": \"13121022996\",\n" +
//                "    \"sex\": 1,\n" +
//                "    \"avatar\": \"\",\n" +
//                "    \"id\": \"1580861173744463874\",\n" +
//                "    \"status\": 0,\n" +
//                "    \"loginIp\": \"\",\n" +
//                "    \"createTime\": \"2022-10-14 18:00:49\",\n" +
//                "    \"deptList\": [\n" +
//                "      {\n" +
//                "        \"id\": \"100\",\n" +
//                "        \"creator\": \"admin\",\n" +
//                "        \"createTime\": \"2021-01-05 17:03:47\",\n" +
//                "        \"updater\": \"103\",\n" +
//                "        \"updateTime\": \"2022-06-25 23:19:03\",\n" +
//                "        \"deleted\": false,\n" +
//                "        \"name\": \"国家机械工业集团\",\n" +
//                "        \"parentId\": \"0\",\n" +
//                "        \"sort\": 0,\n" +
//                "        \"leaderUserId\": \"1\",\n" +
//                "        \"phone\": \"15888888888\",\n" +
//                "        \"email\": \"ry@qq.com\",\n" +
//                "        \"status\": 0,\n" +
//                "        \"admin\": false\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"id\": \"101\",\n" +
//                "        \"creator\": \"admin\",\n" +
//                "        \"createTime\": \"2021-01-05 17:03:47\",\n" +
//                "        \"updater\": \"1\",\n" +
//                "        \"updateTime\": \"2022-08-30 17:50:03\",\n" +
//                "        \"deleted\": false,\n" +
//                "        \"name\": \"北京总公司\",\n" +
//                "        \"parentId\": \"100\",\n" +
//                "        \"sort\": 1,\n" +
//                "        \"leaderUserId\": \"1561606936288215042\",\n" +
//                "        \"phone\": \"15888888888\",\n" +
//                "        \"email\": \"ry@qq.com\",\n" +
//                "        \"status\": 0,\n" +
//                "        \"admin\": false\n" +
//                "      }\n" +
//                "    ],\n" +
//                "    \"postList\": [\n" +
//                "      {\n" +
//                "        \"id\": \"1564817342540419073\",\n" +
//                "        \"creator\": \"1\",\n" +
//                "        \"createTime\": \"2022-08-31 11:28:22\",\n" +
//                "        \"updater\": \"1\",\n" +
//                "        \"updateTime\": \"2022-08-31 13:12:34\",\n" +
//                "        \"deleted\": false,\n" +
//                "        \"code\": \"PM\",\n" +
//                "        \"name\": \"产品经理\",\n" +
//                "        \"sort\": 2,\n" +
//                "        \"status\": 0,\n" +
//                "        \"remark\": \"产品经理\",\n" +
//                "        \"admin\": false\n" +
//                "      }\n" +
//                "    ]\n" +
//                "  },\n" +
//                "  \"msg\": \"成功\"\n" +
//                "}");
        System.out.println("encrypt = " + encrypt);
    }

}
