package com.meijm.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.POST_TYPE;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

@Component
@Slf4j
public class PostFilter extends ZuulFilter {
    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public String filterType() {
        return POST_TYPE;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }
    @Override
    public Object run() {
        Map<String,String> postMap = new HashMap<>();
        postMap.put("post","in post");
        log.info("in POST_TYPE filter :{} content", RequestContext.getCurrentContext());
        return postMap;
    }
}