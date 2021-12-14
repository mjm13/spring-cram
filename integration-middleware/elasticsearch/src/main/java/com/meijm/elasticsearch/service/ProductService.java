package com.meijm.elasticsearch.service;

import com.meijm.elasticsearch.entity.Product;
import com.meijm.elasticsearch.esrepository.ProductRespository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductService {
    @Autowired
    private ProductRespository productRespository;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    
    public void saveAll(List<Product> orders) {
        productRespository.saveAll(orders);
    }

    
    public void deleteAll() {
        productRespository.deleteAll();
    }

    
    public void updateById(Product order) {
        productRespository.save(order);
    }

    
    public List<Product> findList(Product product) {
        CriteriaQuery criteriaQuery = new CriteriaQuery(new Criteria()
                .and(new Criteria("name").contains(product.getName()))
                .and(new Criteria("specifications").is(product.getManufactor()))
                .and(new Criteria("describe").is(product.getDescribe()))
                .and(new Criteria("manufactor").is(product.getSpecifications())));

        SearchHits<Product> searchHits = elasticsearchRestTemplate.search(criteriaQuery, Product.class);
        List<Product> result = searchHits.get().map(SearchHit::getContent).collect(Collectors.toList());
        return result;
    }

}
