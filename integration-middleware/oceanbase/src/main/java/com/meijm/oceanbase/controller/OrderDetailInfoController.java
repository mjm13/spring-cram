package com.meijm.oceanbase.controller;

import com.github.javafaker.Faker;
import com.meijm.oceanbase.entity.OrderDetailInfo;
import com.meijm.oceanbase.service.OrderDetailInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RequestMapping("/orderDetailInfo")
@RestController
public class OrderDetailInfoController {
    @Autowired
    private OrderDetailInfoService service;

    @GetMapping("/mockData")
    public ResponseEntity mockData() {
        Faker faker = Faker.instance(Locale.CHINA);
        List<OrderDetailInfo> datas = new ArrayList<>();
        for (int i = 0; i < 200000L; i++) {
            OrderDetailInfo order = new OrderDetailInfo();
            order.setSkuName(faker.name().title());
            order.setSkuPrice((double) faker.number().randomDigit());
            order.setCount(faker.number().randomDigit());
            order.setCreateTime(faker.date().birthday());
            order.setPackTime(faker.date().birthday());
            order.setActualNum(faker.number().randomDigit());
            order.setPlanNum(faker.number().randomDigit());
            order.setWaveNo(faker.idNumber().invalid());
            order.setCustomerCode(faker.idNumber().invalidSvSeSsn());
            order.setPickNum(faker.number().randomDigit());
            order.setItemId(faker.idNumber().validSvSeSsn());
            order.setState(faker.number().numberBetween(0,5));
            datas.add(order);
            if (i % 200 == 0 || i == 200000L - 1) {
                service.saveBatch(datas);
                datas.clear();
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body("ok");
    }
    
    @GetMapping("/insert")
    public ResponseEntity insert() {
        Faker faker = Faker.instance(Locale.CHINA);
        OrderDetailInfo order = new OrderDetailInfo();
        order.setId(1L);
        order.setSkuName(faker.name().title());
        order.setSkuPrice((double) faker.number().randomDigit());
        order.setCount(faker.number().randomDigit());
        order.setCreateTime(faker.date().birthday());
        order.setPackTime(faker.date().birthday());
        order.setActualNum(faker.number().randomDigit());
        order.setPlanNum(faker.number().randomDigit());
        order.setWaveNo(faker.idNumber().invalid());
        order.setCustomerCode(faker.idNumber().invalidSvSeSsn());
        order.setPickNum(faker.number().randomDigit());
        order.setItemId(faker.idNumber().validSvSeSsn());
        order.setState(faker.number().numberBetween(0,5));
        service.save(order);
        return ResponseEntity.status(HttpStatus.OK).body("ok");
    }

    @GetMapping("/update")
    public ResponseEntity update() {
        Faker faker = Faker.instance(Locale.CHINA);
        OrderDetailInfo order = new OrderDetailInfo();
        order.setId(1L);
        order.setSkuName(faker.name().title());
        order.setSkuPrice((double) faker.number().randomDigit());
        order.setCount(faker.number().randomDigit());
        order.setCreateTime(faker.date().birthday());
        order.setPackTime(faker.date().birthday());
        order.setActualNum(faker.number().randomDigit());
        order.setPlanNum(faker.number().randomDigit());
        order.setWaveNo(faker.idNumber().invalid());
        order.setCustomerCode(faker.idNumber().invalidSvSeSsn());
        order.setPickNum(faker.number().randomDigit());
        order.setItemId(faker.idNumber().validSvSeSsn());
        order.setState(faker.number().numberBetween(0,5));
        service.updateById(order);
        return ResponseEntity.status(HttpStatus.OK).body("ok");
    }

    @GetMapping("/selectOne")
    public ResponseEntity selectOne() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getById(1L));
    }
    
    
}
