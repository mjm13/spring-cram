package com.meijm.toolbox.hutool.json;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

public class JsonOperationDemo {
    public static void main(String[] args) {
        JSONObject data = JSONUtil.createObj();
        JSONUtil.putByPath(data,"aa.bb.cc","test");
        System.out.println(JSONUtil.toJsonStr(data));
        String temp = JSONUtil.getByPath(data,"aa.bb.cc","aaa");
        System.out.println(temp);
    }
}
