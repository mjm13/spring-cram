package com.meijm.udp.controller;

import com.meijm.udp.server.UdpServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private UdpServer udpServer;

    @GetMapping("index")
    public String index(){
        udpServer.sendMessage("测试数据-1");
        return "index";
    }
}
