package com.meijm.security.config.custom;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String secretKey = (String) authentication.getCredentials();
        //自定义校验逻辑
        if(!"123".equals(secretKey)){
            throw new InsufficientAuthenticationException("认证错误");
        }
        Set<String> dbAuthsSet = new HashSet<String>();
        dbAuthsSet.add("customUser");
        Collection<? extends GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(dbAuthsSet.toArray(new String[0]));
//        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new CustomAuthenticationToken(secretKey, secretKey, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return CustomAuthenticationToken.class.isAssignableFrom(authentication);
    }
}