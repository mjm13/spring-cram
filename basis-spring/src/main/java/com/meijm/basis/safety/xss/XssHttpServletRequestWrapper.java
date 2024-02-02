package com.meijm.basis.safety.xss;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * xss过滤包装类
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
    private final Logger logger = LoggerFactory.getLogger(XssHttpServletRequestWrapper.class);
    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request The request to wrap
     * @throws IllegalArgumentException if the request is null
     */
    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getHeader(String name) {
        String strHeader = super.getHeader(name);
        if (StringUtils.isEmpty(strHeader)) {
            return strHeader;

        }
        return Jsoup.clean(super.getHeader(name), Whitelist.relaxed());
    }

    @Override
    public String getParameter(String name) {
        String strParameter = super.getParameter(name);
        if (StringUtils.isEmpty(strParameter)) {
            return strParameter;
        }
        return Jsoup.clean(super.getParameter(name), Whitelist.relaxed());
    }


    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if (values == null) {
            return values;
        }
        int length = values.length;
        String[] escapseValues = new String[length];
        for (int i = 0; i < length; i++) {
            //过滤一切可能的xss攻击字符串
            escapseValues[i] = Jsoup.clean(values[i], Whitelist.relaxed()).trim();
            if (!StringUtils.equals(escapseValues[i], values[i])) {
                logger.info("xss字符串过滤前：" + values[i] + "\t" + "过滤后：" + escapseValues[i]);
            }
        }
        return escapseValues;
    }
}