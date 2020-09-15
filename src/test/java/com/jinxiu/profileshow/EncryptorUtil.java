package com.jinxiu.profileshow;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.EnvironmentStringPBEConfig;
import org.junit.Test;

/**
 * 数据库用户名密码加密工具
 */
public class EncryptorUtil {
    private static StandardPBEStringEncryptor standardPBEStringEncryptor = new StandardPBEStringEncryptor();

    @Test
    public void decry() {
        EnvironmentStringPBEConfig config = new EnvironmentStringPBEConfig();
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setPassword("liky");
        standardPBEStringEncryptor.setConfig(config);
        String plainText = "jdbc:mysql://39.100.149.36:3306/javaDemo?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true";
        String encryedText = standardPBEStringEncryptor.encrypt(plainText);
        System.out.println(encryedText);
    }

    @Test
    public void encry(){
        EnvironmentStringPBEConfig config = new EnvironmentStringPBEConfig();
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setPassword("liky");
        standardPBEStringEncryptor.setConfig(config);
        String encrytedText = "7p1mmjNKZEC/MrqSgXM0ONeFnULh3ZZ2GBp+F53TX2QudvkYNIIyOD350xuSnubMC7BksqguTRhhFq9H7puLmPzY1g6VcVatcHJLyc84Q7KiIJlGGS7smAXUYfIhrXDDs85MQUYLDZ8783GFRMljqg==";
        String plainText = standardPBEStringEncryptor.decrypt(encrytedText);
        System.out.println(plainText);
    }
}
