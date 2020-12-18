package com.meijm.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.ROUTE_TYPE;

@Component
@Slf4j
public class RouteFilter extends ZuulFilter {
    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public String filterType() {
        return ROUTE_TYPE;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }
    @Override
    public Object run() {
        Map<String,String> routeMap = new HashMap<>();
        routeMap.put("route","in route");
        log.info("in ROUTE_TYPE filter :{} content", RequestContext.getCurrentContext());
        return routeMap;
    }
}