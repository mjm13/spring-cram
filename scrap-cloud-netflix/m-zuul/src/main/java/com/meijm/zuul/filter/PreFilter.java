package com.meijm.zuul.filter;

import cn.hutool.json.JSONUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

@Component
@Slf4j
public class PreFilter extends ZuulFilter {
    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        return ctx.sendZuulResponse();
    }
    @Override
    public Object run() {
        Map<String,String> preMap = new HashMap<>();
        preMap.put("pre","返回信息");
//        SEND_FORWARD_FILTER_RAN
        RequestContext ctx = RequestContext.getCurrentContext();
//        ctx.setSendZuulResponse(false);
//        ctx.setResponseStatusCode(200);
//        ctx.set("sendForwardFilter.ran", true);
//        ctx.setResponseBody(JSONUtil.toJsonStr(preMap));
//        ctx.getResponse().setContentType("application/json;charset=UTF-8");
       log.info("in PRE_TYPE filter :{} content",ctx);
        return null;
    }
}