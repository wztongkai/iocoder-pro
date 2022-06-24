package com.iocoder.yudao.server.jasypt;

import com.iocoder.yudao.server.IoCoderApplication;
import com.ulisesbocchio.jasyptspringboot.encryptor.DefaultLazyEncryptor;
import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author wu kai
 * @date 2022/6/24
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IoCoderApplication.class)
public class JasyptTest {

    @Test
    public void test() {
        // 对应配置文件中配置的加密密钥
        System.setProperty("jasypt.encryptor.password", "strangegirl");
        StringEncryptor stringEncryptor = new DefaultLazyEncryptor(new StandardEnvironment());
        System.out.println("用户名加密： " + stringEncryptor.encrypt("root"));
        System.out.println("用户名解密： " + stringEncryptor.decrypt(stringEncryptor.encrypt("root")));

        System.out.println("密码加密： " + stringEncryptor.encrypt("123456"));
        System.out.println("密码解密： " + stringEncryptor.decrypt(stringEncryptor.encrypt("123456")));

    }
}
