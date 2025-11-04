package com.meijm.toolbox.crypto;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.nio.charset.StandardCharsets;
import java.security.*;

public class RsaDemo {
    public static void main(String[] args) throws Exception {
        //应用密钥，在“前台-个人工作台-应用管理”-查看数据功能-获取对应的APP Secret
        String appSecret = "xjgreat";
        Provider provider = new BouncyCastleProvider();
        Security.addProvider(provider);
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(appSecret.getBytes(StandardCharsets.UTF_8));
        // 生成私钥
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", provider);
        generator.initialize(1024, random);
        KeyPair keyPair = generator.generateKeyPair();
        String privateKey = Base64.encodeUrlSafe(keyPair.getPrivate().getEncoded());
        RSA rsa = new RSA(privateKey, null);

        String content = "957a50f4ed0c37ae15fd34b49c7dab728f429a44fc88664a6d71c089397f59e2582719d992e123e374b7b2b00221334430fcd0753449f04ae938cc7a873cc86c3c8697fc6642faf240f34d56bf4727bf050bf543ce9f9422f97ba6bdce5cc9c0";
        byte[] encryptedBytes = rsa.decrypt(content, KeyType.PrivateKey);
        String result = new String(encryptedBytes);
        System.out.println("解密结果: " + result);
    }
}
