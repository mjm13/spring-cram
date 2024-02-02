package com.meijm.oceanbase.config;

import com.alipay.oceanbase.rpc.ObTableClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

//@Configuration
public class OceanBaseConfig {
    
//    @Bean
    public ObTableClient client(){
        ObTableClient obTableClient = new ObTableClient();
        try {
            obTableClient.setOdpMode(true);
            obTableClient.setOdpAddr("192.168.1.252");
            obTableClient.setOdpPort(2883);
            obTableClient.setTenantName("sys");
            obTableClient.setDatabase("upcloud-lite");
            obTableClient.setFullUserName("root@sys");
            obTableClient.setPassword("prolog0212");
//            obTableClient.setSysUserName("root@sys");
//            obTableClient.setSysPassword("prolog0212");
            obTableClient.init();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return obTableClient;
    }
}
