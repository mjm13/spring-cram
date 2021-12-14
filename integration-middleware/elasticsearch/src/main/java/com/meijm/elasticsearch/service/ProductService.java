package com.meijm.elasticsearch.service;

import com.meijm.elasticsearch.entity.Product;
import com.meijm.elasticsearch.esrepository.ProductRespository;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
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
        log.info("elasticsearchRestTemplate.indexOps(Product.class).exists():{}",elasticsearchRestTemplate.indexOps(Product.class).exists());
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery("name", "张"))
                .build();

        SearchHits<Product> searchHits = elasticsearchRestTemplate.search(nativeSearchQuery, Product.class);
        List<Product> result = searchHits.get().map(SearchHit::getContent).collect(Collectors.toList());
        return result;
    }

}
