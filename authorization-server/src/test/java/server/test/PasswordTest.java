package server.test;

import oauth.common.BCryptPasswordEncoder;
import org.junit.Test;


public class PasswordTest {

    @Test
    public  void doPassword(){
        String password="123456";
        BCryptPasswordEncoder encoder =new BCryptPasswordEncoder();
        String newPassword=encoder.encode(password);
        System.out.println("加密后的长度："+newPassword.length());
        System.out.println("加密后的结果："+newPassword);
        System.out.println("解密后的结果："+encoder.matches(password,newPassword));

    }
}
