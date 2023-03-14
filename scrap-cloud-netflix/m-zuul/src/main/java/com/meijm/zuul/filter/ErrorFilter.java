package com.meijm.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.ERROR_TYPE;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

@Component
@Slf4j
public class ErrorFilter extends ZuulFilter {
    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public String filterType() {
        return ERROR_TYPE;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }
    @Override
    public Object run() {
        log.info("in ERROR_TYPE filter :{} content", RequestContext.getCurrentContext());
        return null;
    }
}