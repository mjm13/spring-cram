package com.meijm.basis.rsa;


import cn.hutool.core.codec.Base64;

import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Provider;
import java.security.SecureRandom;

/**
 * 还需要设置环境变量
 * -Djava.security.egd=file:/dev/./urandom  参数作用指定
 * 在k8s环境中变量的名称为JAVA_TOOL_OPTIONS
 * JAVA_OPTS有可能不被读取
 */
public class RsaTest {
    public static void main(String[] args) throws Exception {
//        Provider provider = new org.bouncycastle.jce.provider.BouncyCastleProvider();
//        Security.addProvider(provider);
//        SecureRandom random = new SecureRandom("aa".getBytes(StandardCharsets.UTF_8));
//        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", provider);
//        generator.initialize(512, random);
//        KeyPair keyPair = generator.generateKeyPair();
//        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", provider);
//
//        PublicKey publicKey = keyPair.getPublic();
//        System.out.println(Base64.encode(publicKey.getEncoded()));
//        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
//        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
//        random.setSeed("aa".getBytes());
        SecureRandom random = new SecureRandom("aa".getBytes(StandardCharsets.UTF_8));
        System.out.println("Algorithm: " + random.getAlgorithm());
        Provider provider = new org.bouncycastle.jce.provider.BouncyCastleProvider();
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", provider);
        generator.initialize(512, random);
        KeyPair keyPair = generator.generateKeyPair();
        System.out.println("第一次密钥:"+Base64.encode(keyPair.getPublic().getEncoded()));

        generator = KeyPairGenerator.getInstance("RSA", provider);
        generator.initialize(512, random);
        keyPair = generator.generateKeyPair();
        System.out.println("第二次密钥:"+Base64.encode(keyPair.getPublic().getEncoded()));
    }
}
