package com.meijm.liteflow.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.flow.LiteflowResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping(value = "demo")
public class DemoController {
    @Resource
    private FlowExecutor flowExecutor;
    @RequestMapping(value = "test", method = RequestMethod.GET)
    public Map<String,Object> test() {
        LiteflowResponse response = flowExecutor.execute2Resp("chain1", "arg");
        return ImmutableMap.of("chainId",response.getChainId());
    }
}
