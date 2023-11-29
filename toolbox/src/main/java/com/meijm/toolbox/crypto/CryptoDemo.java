package com.meijm.toolbox.crypto;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CryptoDemo {
    public static void main(String[] args) {
        String text = "我是一段测试aaaa";

        SM2 sm2 = SmUtil.sm2();
// 公钥加密，私钥解密
        String encryptStr = sm2.encryptBcd(text, KeyType.PublicKey);
        
        String decryptStr = StrUtil.utf8Str(sm2.decryptFromBcd(encryptStr, KeyType.PrivateKey));
        log.info("text:{}",text);      
        log.info("encryptStr:{}",encryptStr);      
        log.info("decryptStr:{}",decryptStr);      
    }
}
