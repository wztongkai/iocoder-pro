package com.iocoder.yudao.server.jasypt;


import com.iocoder.yudao.server.IoCoderApplication;
import org.jasypt.util.text.BasicTextEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author wu kai
 * @date 2022/6/24
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IoCoderApplication.class)
public class JasyptTest {

    @Test
    public void test() {
        // 对应配置文件中配置的加密密钥
//        System.setProperty("jasypt.encryptor.password", "Bt%XJ^n1j8mz");
//        StringEncryptor stringEncryptor = new DefaultLazyEncryptor(new StandardEnvironment());
//        System.out.println("用户名加密： " + stringEncryptor.encrypt("root"));
//        System.out.println("用户名解密： " + stringEncryptor.decrypt(stringEncryptor.encrypt("root")));
//
//        System.out.println("密码加密： " + stringEncryptor.encrypt("123456"));
//        System.out.println("密码解密： " + stringEncryptor.decrypt(stringEncryptor.encrypt("123456")));

        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword("WkZuiShuai");
        String username = textEncryptor.encrypt("root");
        String password = textEncryptor.encrypt("123aa-4567");
        System.out.println("username: " + username);
        System.out.println("password:" + password);
    }
}
