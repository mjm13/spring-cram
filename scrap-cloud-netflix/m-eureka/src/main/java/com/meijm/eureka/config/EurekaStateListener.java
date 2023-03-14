package com.meijm.eureka.config;

import com.netflix.appinfo.InstanceInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.eureka.server.event.*;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EurekaStateListener {

    @EventListener(condition = "#event.replication==false")
    public void listen(EurekaInstanceCanceledEvent event) {
        String msg="服务"+event.getAppName()+"\n"+event.getServerId()+"已下线";
        log.info(msg);
    }

    @EventListener(condition = "#event.replication==false")
    public void listen(EurekaInstanceRegisteredEvent event) {
        InstanceInfo instanceInfo = event.getInstanceInfo();
        String msg="服务"+instanceInfo.getAppName()+"\n"+  instanceInfo.getHostName()+":"+ instanceInfo.getPort()+ " \nip: " +instanceInfo.getIPAddr() +"进行注册";
        log.info(msg);

    }

    @EventListener(condition = "#event.replication==false")
    public void listen(EurekaInstanceRenewedEvent event) {
        log.info("服务{}进行续约", event.getServerId() +"  "+ event.getAppName());
    }

    @EventListener
    public void listen(EurekaRegistryAvailableEvent event) {
        log.info("注册中心启动,{}", System.currentTimeMillis());
    }

    @EventListener
    public void listen(EurekaServerStartedEvent event) {
        log.info("注册中心服务端启动,{}", System.currentTimeMillis());
    }

}
