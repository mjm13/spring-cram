package com.meijm.oauth2.vo;

import cn.hutool.core.collection.CollectionUtil;
import com.meijm.oauth2.entity.SysOauthClientDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.provider.ClientDetails;

import java.util.Collection;
import java.util.Set;

/**
 * 客户端登陆身份
 *
 * @author ruoyi
 */
public class LoginClientDetails extends SysOauthClientDetails implements ClientDetails {

    @Override
    public boolean isAutoApprove(String scope) {
        return false;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities(){
        Collection<GrantedAuthority> authorities = null;
        Set<String> dbAuthorities = getDbAuthorities();
        if(CollectionUtil.isNotEmpty(dbAuthorities)){
            authorities = AuthorityUtils
                    .createAuthorityList(dbAuthorities.toArray(new String[0]));
        }
        return authorities;
    }
    @Override
    public boolean isScoped() {
        return getScope() != null && !getScope().isEmpty();
    }
    @Override
    public boolean isSecretRequired() {
        return getClientSecret() != null;
    }
}
