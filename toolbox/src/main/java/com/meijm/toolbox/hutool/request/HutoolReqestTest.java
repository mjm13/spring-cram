package com.meijm.toolbox.hutool.request;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Slf4j
public class HutoolReqestTest {
    public static void main(String[] args) {
        String baseUrl = "https://api-inference.huggingface.co/models/meta-llama/Llama-3.2-11B-Vision-Instruct/v1/chat/completions";
        HttpResponse response = HttpRequest.post(baseUrl).header("Authorization","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").body("{\n" +
                "    \"model\": \"meta-llama/Llama-3.2-11B-Vision-Instruct\",\n" +
                "    \"messages\": [\n" +
                "\t\t{\n" +
                "\t\t\t\"role\": \"user\",\n" +
                "\t\t\t\"content\": [\n" +
                "\t\t\t\t{\n" +
                "\t\t\t\t\t\"type\": \"text\",\n" +
                "\t\t\t\t\t\"text\": \"Describe this image in one sentence.\"\n" +
                "\t\t\t\t},\n" +
                "\t\t\t\t{\n" +
                "\t\t\t\t\t\"type\": \"image_url\",\n" +
                "\t\t\t\t\t\"image_url\": {\n" +
                "\t\t\t\t\t\t\"url\": \"https://cdn.britannica.com/61/93061-050-99147DCE/Statue-of-Liberty-Island-New-York-Bay.jpg\"\n" +
                "\t\t\t\t\t}\n" +
                "\t\t\t\t}\n" +
                "\t\t\t]\n" +
                "\t\t}\n" +
                "\t],\n" +
                "    \"max_tokens\": 500,\n" +
                "    \"stream\": true\n" +
                "}","application/json").execute();
        System.out.println(response.body());
        
//        String baseUrl = "http://192.168.1.22:6001/data/status";
//        String result =HttpRequest.get(baseUrl).cogokie("JSESSIONID=FD65BB9397086D2380E8985B7D9BFD50").execute().body();
////        log.info(result);
//        JSONObject datas = JSONUtil.parseObj(result);
//        JSONArray apps = datas.getJSONArray("apps");
//        String cvs = apps.toList(JSONObject.class).stream().map(app -> {
//            JSONObject instance = app.getJSONArray("instanceInfos").getJSONObject(0).getJSONArray("instances").getJSONObject(0);
//            return StrUtil.format("{},{},{},{}",instance.getStr("name"),instance.getStr("group","-"),instance.getStr("id"),instance.getStr("status"));
//        }).collect(Collectors.joining("\n"));
//        FileUtil.writeString("NAME,GROUP,IP,STATUS\n"+cvs,"D:\\服务列表.csv", StandardCharsets.UTF_8);
    }
}
