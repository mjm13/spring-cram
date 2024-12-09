package com.meijm.security.config.custom;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

public class CustomAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public CustomAuthenticationFilter() {
        super(new AntPathRequestMatcher("/custom/**"));
        //
        setContinueChainBeforeSuccessfulAuthentication(true);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        CustomAuthenticationToken authentication = null;
        try {
            Enumeration<String> headers = request.getHeaders("secretKey");
            String secretKey = headers.nextElement();
            // 通过request中参数构建自定义token,与CustomAuthenticationProvider对应即可
            authentication = new CustomAuthenticationToken(secretKey, secretKey);
            //设置附属信息,sessionid,ip
            setDetails(request, authentication);
            //通过Provider验证token
            
            authentication = (CustomAuthenticationToken) getAuthenticationManager().authenticate(authentication);
            //
//            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            throw new AuthenticationServiceException("secretKey认证失败",e);
        }
        return authentication;
    }

    protected void setDetails(HttpServletRequest request,
                              CustomAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }
}
