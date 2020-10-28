package com.meijm.oauth2.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.meijm.oauth2.frame.SysJsonTypeHandler;
import lombok.Data;

import java.util.Map;
import java.util.Set;

@Data
@TableName(autoResultMap = true)
public class SysOauthClientDetails {
    @TableId
    private String clientId;
    @TableField(typeHandler = SysJsonTypeHandler.class)
    private Set<String> resourceIds;
    private String clientSecret;
    @TableField(typeHandler = SysJsonTypeHandler.class)
    private Set<String> scope;
    @TableField(typeHandler = SysJsonTypeHandler.class)
    private Set<String> authorizedGrantTypes;
    @TableField(typeHandler = SysJsonTypeHandler.class)
    private Set<String> registeredRedirectUri;
    @TableField(typeHandler = SysJsonTypeHandler.class)
    private Set<String> dbAuthorities;
    private Integer accessTokenValiditySeconds;
    private Integer refreshTokenValiditySeconds;
    @TableField(typeHandler = SysJsonTypeHandler.class)
    private Map<String, Object> additionalInformation;
}
