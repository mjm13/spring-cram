package com.meijm.toolbox.hutool.request;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Slf4j
public class HutoolReqestTest {
    public static void main(String[] args) {
        String baseUrl = "http://192.168.1.22:6001/data/status";
        String result =HttpRequest.get(baseUrl).cookie("JSESSIONID=FD65BB9397086D2380E8985B7D9BFD50").execute().body();
//        log.info(result);
        JSONObject datas = JSONUtil.parseObj(result);
        JSONArray apps = datas.getJSONArray("apps");
        String cvs = apps.toList(JSONObject.class).stream().map(app -> {
            JSONObject instance = app.getJSONArray("instanceInfos").getJSONObject(0).getJSONArray("instances").getJSONObject(0);
            return StrUtil.format("{},{},{},{}",instance.getStr("name"),instance.getStr("group","-"),instance.getStr("id"),instance.getStr("status"));
        }).collect(Collectors.joining("\n"));
        FileUtil.writeString("NAME,GROUP,IP,STATUS\n"+cvs,"D:\\服务列表.csv", StandardCharsets.UTF_8);
    }
}
