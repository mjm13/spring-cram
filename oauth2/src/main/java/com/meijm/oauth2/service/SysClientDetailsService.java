package com.meijm.oauth2.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meijm.oauth2.entity.SysOauthClientDetails;
import com.meijm.oauth2.mapper.SysOauthClientDetailsMapper;
import com.meijm.oauth2.vo.LoginClientDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.provider.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SysClientDetailsService extends ServiceImpl<SysOauthClientDetailsMapper, SysOauthClientDetails> implements ClientDetailsService, ClientRegistrationService {

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        SysOauthClientDetails client = getById(clientId);
        LoginClientDetails loginClient = new LoginClientDetails();
        BeanUtil.copyProperties(client, loginClient);
        return loginClient;
    }

    @Override
    public void addClientDetails(ClientDetails clientDetails) throws ClientAlreadyExistsException {
        SysOauthClientDetails client = new SysOauthClientDetails();
        BeanUtil.copyProperties(clientDetails, client);
        save(client);
    }

    @Override
    public void updateClientDetails(ClientDetails clientDetails) throws NoSuchClientException {
        SysOauthClientDetails client = new SysOauthClientDetails();
        BeanUtil.copyProperties(clientDetails, client);
        updateById(client);
    }

    @Override
    public void updateClientSecret(String clientId, String secret) throws NoSuchClientException {
        UpdateWrapper<SysOauthClientDetails> wrapper = new UpdateWrapper<>();
        wrapper.set("secret", secret);
        wrapper.eq("client_id", clientId);
        update(wrapper);
    }

    @Override
    public void removeClientDetails(String clientId) throws NoSuchClientException {
        removeById(clientId);
    }

    @Override
    public List<ClientDetails> listClientDetails() {
        return this.list().stream().map(sysOauthClientDetails -> {
            LoginClientDetails loginClient = new LoginClientDetails();
            BeanUtil.copyProperties(sysOauthClientDetails, loginClient);
            return loginClient;
        }).collect(Collectors.toList());
    }
}
