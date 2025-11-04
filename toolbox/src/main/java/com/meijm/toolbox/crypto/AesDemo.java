package com.meijm.toolbox.crypto;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.symmetric.AES;

import java.nio.charset.StandardCharsets;

public class AesDemo {
    public static void main(String[] args) {
        //应用密钥，在“前台-个人工作台-应用管理”-查看数据功能-获取对应的APP Secret
        String appSecret = "400599914c903c069fec4cbce78138d442bc7cec";
        String content = "957a50f4ed0c37ae15fd34b49c7dab728f429a44fc88664a6d71c089397f59e2582719d992e123e374b7b2b00221334430fcd0753449f04ae938cc7a873cc86c3c8697fc6642faf240f34d56bf4727bf050bf543ce9f9422f97ba6bdce5cc9c0";
        appSecret = StrUtil.fillAfter(appSecret, '1', 32).substring(0, 16);
        AES aes = new AES(appSecret.getBytes(StandardCharsets.UTF_8));
        byte[] encryptedBytes = HexUtil.decodeHex(content);
        String result  = aes.decryptStr(encryptedBytes);
        System.out.println("解密结果：" + result);
    }
}
