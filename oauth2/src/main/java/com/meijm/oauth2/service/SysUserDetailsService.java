package com.meijm.oauth2.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meijm.oauth2.entity.SysUser;
import com.meijm.oauth2.mapper.SysUserMapper;
import com.meijm.oauth2.vo.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * 用户信息处理
 *
 * @author ruoyi
 */
@Slf4j
@Service
public class SysUserDetailsService extends ServiceImpl<SysUserMapper, SysUser> implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", username);
        SysUser sysUser = getOne(wrapper);
        Set<String> dbAuthsSet = new HashSet<String>();
        dbAuthsSet.add("admin");
        Collection<? extends GrantedAuthority> authorities = AuthorityUtils
                .createAuthorityList(dbAuthsSet.toArray(new String[0]));
        LoginUser user = new LoginUser(sysUser.getId(), sysUser.getUserName(), sysUser.getPassword(), authorities);
        return user;
    }
}
