package com.meijm.toolbox.rsa;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;

public class RSAUtil {

    private static final KeyPair keyPair = initKey();
    //匹配前端JSEncrypt 库加密规则
    private static final String var0="RSA/ECB/PKCS1Padding";
    //    private static final String var0="RSA/None/OAEPWITHSHA-256ANDMGF1PADDING";
    private static KeyPair initKey() {
        try {
//            Provider provider =new org.bouncycastle.jce.provider.BouncyCastleProvider();
//            Security.addProvider(provider);
            SecureRandom random = new SecureRandom();
//            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", provider);
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", "BC");
            generator.initialize(1024,random);
            return generator.generateKeyPair();
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String generateBase64PublicKey() {
        PublicKey publicKey = keyPair.getPublic();
        return Base64.encodeBase64String(publicKey.getEncoded());
    }

    public static String decryptBase64(String string) {
        return new String(
                decrypt(Base64.decodeBase64(string.getBytes(StandardCharsets.UTF_8)))
                ,StandardCharsets.UTF_8);
    }

    public static String encryptBase64(String string) {
        return Base64.encodeBase64String(encrypt(string.getBytes(StandardCharsets.UTF_8)));
    }

    private static byte[] decrypt(byte[] byteArray) {
        try {
//            Provider provider = new org.bouncycastle.jce.provider.BouncyCastleProvider();
//            Security.addProvider(provider);
//            Cipher cipher = Cipher.getInstance(var0, provider);
            Cipher cipher = Cipher.getInstance(var0, "BC");
            PrivateKey privateKey = keyPair.getPrivate();
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] plainText = cipher.doFinal(byteArray);
            return plainText;
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static byte[] encrypt(byte[] byteArray){
        try {
//            Provider provider = new org.bouncycastle.jce.provider.BouncyCastleProvider();
//            Security.addProvider(provider);
//            Cipher cipher = Cipher.getInstance(var0, provider);
            Cipher cipher = Cipher.getInstance(var0, "BC");
            PublicKey publicKey = keyPair.getPublic();
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] plainText = cipher.doFinal(byteArray);
            return plainText;
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

}
