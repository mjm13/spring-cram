package com.meijm.basis;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;

public class Hello {
    public static void main(String[] args) {
        String accessKeyId = "ywkrivgdsrgmf13prwhh2e6g981k1n4l";
        String accessKeySecret = "7vponk5rzpfco9u24z1j132k29ynctsfxnak94zmu82kroxvij7167cwduo54mdb";
        String accessTime= Long.toString(System.currentTimeMillis());
        String accessSign= SecureUtil.md5(accessTime+"_"+accessKeySecret);

//        String url = String.format("https://10.200.144.235:8080/mask-api/openApi/algorithmList?accessKeyId=%s&accessTime=%s&accessSign=%s",
//                accessKeyId,accessTime,accessSign);
//        System.out.println(url);
//        String result = HttpUtil.get(url);
//        System.out.println(result);
//   https://210.41.216.67:60201
//        System.setProperty("https.proxyHost", "210.41.216.67");
//        System.setProperty("https.proxyPort", "60201");
//        System.setProperty("https.proxyUser", "ztsj4");
//        System.setProperty("https.proxyPassword", "liu@12345");
//        System.setProperty("java.net.useSystemProxies", "true");
//        System.clearProperty("http.proxyHost");
//        System.clearProperty("https.proxyHost");


//        System.setProperty("java.net.useSystemProxies", "true");
//        System.setProperty("java.net.preferIPv4Stack", "true");
//        String baseUrl = "https://10.200.144.235:8080/mask-api/openApi/algorithmList?" +
//                "accessKeyId=ywkrivgdsrgmf13prwhh2e6g981k1n4l" +
//                "&accessTime=1752141958199&accessSign=8858bc80daf326ee3e48e9eed282e14c";
//        String result = HttpRequest.get(baseUrl)
//                .timeout(5000)
//                .execute().body();
//        System.out.println(result);


    }
}
