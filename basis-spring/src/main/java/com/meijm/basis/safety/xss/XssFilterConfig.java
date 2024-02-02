package com.meijm.basis.safety.xss;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import javax.servlet.DispatcherType;
import java.util.HashMap;
import java.util.Map;

/**
 * 设置跨站脚本过滤
 */
public class XssFilterConfig {
    @Value("${xssSecurity.enabled:false}")
    private String enabled;

    @Value("${xssSecurity.excludes:}")
    private String excludes;

    @Value("${xssSecurity.urlPatterns:}")
    private String urlPatterns;

    @Bean
    public FilterRegistrationBean xssFilterRegistration(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setDispatcherTypes(DispatcherType.REQUEST);
        registrationBean.setFilter(new XssFilter());
        registrationBean.addUrlPatterns(StringUtils.split(urlPatterns,","));
        registrationBean.setName("XssFilter");
        registrationBean.setOrder(9999);
        Map<String,String> initParameters = new HashMap<>();
        initParameters.put("excludes",excludes);
        initParameters.put("enabled",enabled);
        registrationBean.setInitParameters(initParameters);
        return registrationBean;
    }
}