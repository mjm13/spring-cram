package com.meijm.toolbox.crypto;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.nio.charset.StandardCharsets;
import java.security.*;

public class RsaDemo1 {
    public static void main(String[] args) throws Exception {
        String appSecret = "2f590f90ad74139c0c86772ba55abdfdd6435fae";
        Provider provider = new BouncyCastleProvider();
        Security.addProvider(provider);
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(appSecret.getBytes(StandardCharsets.UTF_8));
        // 生成私钥
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", provider);
        generator.initialize(1024, random);
// 生成密钥对
        KeyPair keyPair = generator.generateKeyPair();

        String publicKey = Base64.encodeUrlSafe(keyPair.getPublic().getEncoded());
        String privateKey = Base64.encodeUrlSafe(keyPair.getPrivate().getEncoded());

        System.out.println("公钥: " + publicKey);
        System.out.println("私钥: " + privateKey);

        RSA rsa = new RSA(privateKey, publicKey);

// 加密
//        String data = "张三";
//        byte[] encrypted = rsa.encrypt(data, KeyType.PublicKey);
//        String encryptedBase64 = Base64.encodeUrlSafe(encrypted);
//        System.out.println("加密后: " + encryptedBase64);

// 解密
        String encryptedBase64 = "LNItBOIyZD5avsOXQ3SOvN7irJINi5OJo65uXJVVALTpG-OJrVjkYsARIOerH7-sdsac4hidEx36zqtu1PfGArzxU4HeVxpsaQz_49pqM_YqjGBky4pouqxhU_fcHqWDTnV6Xrpzf1fzy09SywHvIltMOVydVAli27b4h8B0CeE";
        byte[] decrypted = rsa.decrypt(encryptedBase64, KeyType.PrivateKey);
        System.out.println("解密后: " + new String(decrypted));
    }
}
