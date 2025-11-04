package com.meijm.basis.thread;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.BCUtil;
import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.AsymmetricCrypto;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;
import cn.hutool.crypto.digest.Digester;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.SM4;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.spec.IvParameterSpec;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.SQLException;


/**
 * @author wangzijun
 */
public class DecryptUtil {

    private static final String PREFIX = "xj-encrypt-";
    private static final String key = "pangu123pangu123";

    private static final Logger log = LoggerFactory.getLogger(DecryptUtil.class);

    public static void main(String[] args) {
        System.out.println(decryptDbpwd("xj-encrypt-dc53ab953b6b5c9db42226421a42571889db1da93ef799288b89696b9d9807b8"));
    }

    public static String decryptDbpwd(String encrypted) {
        String result = "";
        byte[] keyBytes = key.getBytes();
        AES aes = new AES(keyBytes);
        try {
            if (encrypted != null && encrypted.startsWith(PREFIX)) {
                result = new String(aes.decrypt(HexUtil.decodeHex(encrypted.substring(PREFIX.length())))); // 调用你的解密方法
            }else {
                result = encrypted;
            }
        }  catch (Exception e) {
            log.error("解密错误,请检查秘钥配置",e);
        }
        return result;
    }

}
