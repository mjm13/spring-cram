package com.meijm.mongodb.controller;

import com.github.javafaker.Faker;
import com.meijm.mongodb.data.OrderDetailInfo;
import com.meijm.mongodb.data.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@RestController
public class UserController {
    
    @Autowired
    private MongoTemplate mongoTemplate;

    @PostMapping("/users")
    public void saveUser(@RequestBody User user) {
        mongoTemplate.save(user);
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return mongoTemplate.findAll(User.class);
    }

    @GetMapping("/findAll")
    public List<Map> findAll() {
        return mongoTemplate.findAll(Map.class,"users");
    }

    @GetMapping("/mockData")
    public void mockData(Long count) {
        Faker faker = Faker.instance(Locale.CHINA);
        if (count == null) {
            count = 40000000L;
        }
        List<OrderDetailInfo> datas = new ArrayList<>();
        for (long i = 0; i < count; i++) {
            OrderDetailInfo order = new OrderDetailInfo();
            order.setId(i);
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
            mongoTemplate.save(order);
        }
    }
}
