package com.meijm.toolbox.crypto;

import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.BCUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;

import java.nio.charset.StandardCharsets;

public class Sm2Demo {
    public static void main(String[] args) {
        /**
         * 公钥私钥为固定内容
         */
        String privateKey = "55d5a83daeacfcebd2c21e61690ae9c30a2fb887793109fe2fd13afade11dfc8";
        String publicKey = "8f7c9b235ce19bc14a94c6affa2592d74e69978123620cb0c06d3edfe87fd37aa0683beb4db1433e218a043a1d0fab670bb758afaae996370b32d68e95b1b805";
        String content = "040d191974eccaa8c360c7b5e30db04be784aa65eb5091dba470f416371dd43adf3ce758cdfbd390bcc48e61876efe6958a4663fa62dd9a4e88453e7f299191bec9b96ef70b86b58765bdced1497e505fede02457b8b0cc5e69af7f85a3c0e67eaeb8c3a3f12ba";

        SM2 sm2 = new SM2(BCUtil.toSm2Params(privateKey), BCUtil.toSm2Params(publicKey.substring(0, 64), publicKey.substring(64, 128)));
        String result = new String(sm2.decrypt(HexUtil.decodeHex(content), KeyType.PrivateKey), StandardCharsets.UTF_8);
        System.out.println("解密结果：" + result);
    }
}
