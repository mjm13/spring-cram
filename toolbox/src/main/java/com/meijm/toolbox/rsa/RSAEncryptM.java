package com.meijm.toolbox.rsa;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

public class RSAEncryptM {
	public static  KeyPair keyPair=genKeyPair(); //用于封装随机产生的公钥与私钥
	public static void main(String[] args) throws Exception {
		//生成公钥和私钥
		genKeyPair();
		//加密字符串
		String message = "df723820";
//		System.out.println("随机生成的公钥为:" + keyMap.get(0));
//		System.out.println("随机生成的私钥为:" + keyMap.get(1));
		String messageEn = encrypt(message);
		System.out.println(message + "\t加密后的字符串为:" + messageEn);
		String messageDe = decrypt(messageEn);
		System.out.println("还原后的字符串为:" + messageDe);
	}

	/** 
	 * 随机生成密钥对 
	 * @throws NoSuchAlgorithmException 
	 */  
	public static KeyPair genKeyPair()  {
		try {
			// KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
			KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
			// 初始化密钥对生成器，密钥大小为96-1024位
			keyPairGen.initialize(512,new SecureRandom());
			// 生成一个密钥对，保存在keyPair中
			return keyPairGen.generateKeyPair();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}  

	public static String encrypt( String str) throws Exception{
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());
		String outStr = Base64.encodeBase64String(cipher.doFinal(str.getBytes("UTF-8")));
		return outStr;
	}

	public static String decrypt(String str) throws Exception{
		byte[] inputByte = Base64.decodeBase64(str.getBytes("UTF-8"));
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
		String outStr = new String(cipher.doFinal(inputByte));
		return outStr;
	}

}