package com.meijm.elasticsearch.controller;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.apifan.common.random.source.AreaSource;
import com.apifan.common.random.source.FinancialSource;
import com.apifan.common.random.source.InternetSource;
import com.apifan.common.random.source.OtherSource;
import com.meijm.elasticsearch.entity.Product;
import com.meijm.elasticsearch.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequestMapping("/product")
@RestController
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/saveAll")
    public void saveAll() {
        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
        List<Product> products = IntStream.range(0, 5000).parallel().mapToObj(operand -> {
            Product product = new Product();
            product.setId(snowflake.nextId());
            product.setName(OtherSource.getInstance().randomChinese(5));
            product.setCompany(FinancialSource.getInstance().randomBseStock()[0]);
            product.setOrigin(AreaSource.getInstance().randomProvince());
            product.setManufactor(OtherSource.getInstance().randomCompanyName(product.getOrigin()));
            product.setSpecifications(OtherSource.getInstance().randomEAN());
            return product;
        }).collect(Collectors.toList());
        productService.saveAll(products);
    }

    @PostMapping("/deleteAll")
    public void deleteAll() {
        productService.deleteAll();
    }

    @PostMapping("/nativeQuery")
    public List<Product> nativeQuery() {
        return productService.nativeQuery();
    }

    @PostMapping("/stringQuery")
    public List<Product> stringQuery() {
        return productService.stringQuery();
    }

    @PostMapping("/criteriaQuery")
    public List<Product> criteriaQuery() {
        return productService.criteriaQuery();
    }

    @PostMapping("/aggregationsQuery")
    public Map<String,Object> aggregationsQuery() {
        return productService.aggregationsQuery();
    }
}
