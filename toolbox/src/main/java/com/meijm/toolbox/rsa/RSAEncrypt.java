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

public class RSAEncrypt {
	private static Map<Integer, String> keyMap = new HashMap<Integer, String>();  //用于封装随机产生的公钥与私钥
	public static void main(String[] args) throws Exception {
		//生成公钥和私钥
		genKeyPair();
		//加密字符串
		String message = "df723820";
		System.out.println("随机生成的公钥为:" + keyMap.get(0));
		System.out.println("随机生成的私钥为:" + keyMap.get(1));

		for (int i = 0; i < 1000; i++) {
			String messageEn = encrypt(message,keyMap.get(0));
			System.out.println(message + "\t加密后的字符串为:" + messageEn);
			String messageDe = decrypt(messageEn,keyMap.get(1));
			System.out.println("还原后的字符串为:" + messageDe);
		}


//		keyMap.put(0,"MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAIrEOY78CUCM8jLUF8bjxMI5c9wVDHHVzeWWI3cgYn9TvDLgZ+rkjcxoWqLo9I/3pyRy5r4FhPUv0C9FfHEU0jkCAwEAAQ==");
//		keyMap.put(1,"MIIBUwIBADANBgkqhkiG9w0BAQEFAASCAT0wggE5AgEAAkEAisQ5jvwJQIzyMtQXxuPEwjlz3BUMcdXN5ZYjdyBif1O8MuBn6uSNzGhaouj0j/enJHLmvgWE9S/QL0V8cRTSOQIDAQABAkBTfg1DwtGuTR/NBV/pA1J3qinT2bZLJ+FiqhtcqdeZ09p+EDgvnxaaelrnsQ+yOS4iHynS6iC46XHo/5F5YDOhAiEA2+UL4zFj3kFEB1Hv6MkYA+en7hCFZ8c2zTzagZEVSmcCIQChjRKLD+ElK/x0VdRHOVaSff3yy5k6ZpINlAmvWgpaXwIgIb9TRbjsxM1rbVwr36QNJXxGD4lFkBnI1VLDjiMAC30CIFCvgL9routJ2MDRzT/1Z0OrIZaOEW2VpQSbF2s48UrJAiBHuuZtab1STtWjjySi4tD3+R2SH8/9XslR+kIDbKyX/Q==");
//
//		String messageDe = decrypt("NW7czLOQvBKt0FECVLeo44CaZKHNPNmv76nCHiePGtiK0A9WaEneOJb47NM+z4RRiAKFkVZ2D3Lga02NxpbZ7A==",keyMap.get(1));
//		System.out.println("还原后的字符串为:" + messageDe);
	}

	/** 
	 * 随机生成密钥对 
	 * @throws NoSuchAlgorithmException 
	 */  
	public static void genKeyPair() throws NoSuchAlgorithmException {
		// KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象  
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
		// 初始化密钥对生成器，密钥大小为96-1024位  
		keyPairGen.initialize(512,new SecureRandom());
		// 生成一个密钥对，保存在keyPair中  
		KeyPair keyPair = keyPairGen.generateKeyPair();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();   // 得到私钥
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  // 得到公钥
		String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded()));
		// 得到私钥字符串  
		String privateKeyString = new String(Base64.encodeBase64((privateKey.getEncoded())));  
		// 将公钥和私钥保存到Map
		keyMap.put(0,publicKeyString);  //0表示公钥
		keyMap.put(1,privateKeyString);  //1表示私钥
	}  
	/** 
	 * RSA公钥加密 
	 *  
	 * @param str 
	 *            加密字符串
	 * @param publicKey 
	 *            公钥 
	 * @return 密文 
	 * @throws Exception 
	 *             加密过程中的异常信息 
	 */  
	public static String encrypt( String str, String publicKey ) throws Exception{
		//base64编码的公钥
		byte[] decoded = Base64.decodeBase64(publicKey);
		RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
		//RSA加密
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, pubKey);
		String outStr = Base64.encodeBase64String(cipher.doFinal(str.getBytes("UTF-8")));
		return outStr;
	}

	/** 
	 * RSA私钥解密
	 *  
	 * @param str 
	 *            加密字符串
	 * @param privateKey 
	 *            私钥 
	 * @return 铭文
	 * @throws Exception 
	 *             解密过程中的异常信息 
	 */  
	public static String decrypt(String str, String privateKey) throws Exception{
		//64位解码加密后的字符串
		byte[] inputByte = Base64.decodeBase64(str.getBytes("UTF-8"));
		//base64编码的私钥
		byte[] decoded = Base64.decodeBase64(privateKey);  
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").
				generatePrivate(new PKCS8EncodedKeySpec(decoded));
		//RSA解密
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, priKey);
		String outStr = new String(cipher.doFinal(inputByte));
		return outStr;
	}

}