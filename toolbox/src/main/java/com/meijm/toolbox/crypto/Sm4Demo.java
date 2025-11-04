package com.meijm.toolbox.crypto;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.BCUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;
import cn.hutool.crypto.symmetric.SM4;

import java.nio.charset.StandardCharsets;

public class Sm4Demo {
    public static void main(String[] args) {
        //应用密钥，在“前台-个人工作台-应用管理”-查看数据功能-获取对应的APP Secret
        String appSecret = "400599914c903c069fec4cbce78138d442bc7cec";
        String content = "773295c0131931df47420a671e859bfd21c1a17ea1ab59a996da5d86343890fe4d58a956b7044882b08b6fb59aa0551349f621ff9ce68e6091276bc17416c6dc4f5940b6b5d0cf29d2cf1b17b1b3063b3c7720801dbd5eaf66e2d3179c45b0f6";
        appSecret = StrUtil.fillAfter(appSecret, '1', 32).substring(0, 16);
        System.out.println(appSecret);
        SM4 sm4 = new SM4(appSecret.getBytes(StandardCharsets.UTF_8));
        byte[] encryptedBytes = HexUtil.decodeHex(content);
        String result = sm4.decryptStr(encryptedBytes);
        System.out.println("解密结果：" + result);
    }
}