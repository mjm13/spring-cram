package com.meijm.elasticsearch.service;

import com.meijm.elasticsearch.entity.Product;
import com.meijm.elasticsearch.esrepository.ProductRespository;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
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

    //https://docs.spring.io/spring-data/elasticsearch/docs/current/reference/html/#elasticsearch.operations
    public List<Product> nativeQuery() {
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery("name", "张"))
                .build();
        SearchHits<Product> searchHits = elasticsearchRestTemplate.search(nativeSearchQuery, Product.class);
        List<Product> result = searchHits.get().map(SearchHit::getContent).collect(Collectors.toList());
        return result;
    }

    public Map<String,Object> aggregationsQuery(){
        String name = "OriginAgg";
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        queryBuilder.addAggregation(AggregationBuilders.terms(name).field("origin"));
        SearchHits<Product> searchHits =elasticsearchRestTemplate.search(queryBuilder.build(),Product.class);
        Aggregations aggregations = searchHits.getAggregations();
        return aggregations.get(name).getMetaData();
    }

    public List<Product> criteriaQuery() {
        Criteria criteria = new Criteria("name").is("张");
        Query query = new CriteriaQuery(criteria);

        SearchHits<Product> searchHits = elasticsearchRestTemplate.search(query, Product.class);
        List<Product> result = searchHits.get().map(SearchHit::getContent).collect(Collectors.toList());
        return result;
    }

    public List<Product> stringQuery() {
        Query query = new StringQuery("{ \"match\": { \"name\": { \"query\": \"张\" } } } ");

        SearchHits<Product> searchHits = elasticsearchRestTemplate.search(query, Product.class);
        List<Product> result = searchHits.get().map(SearchHit::getContent).collect(Collectors.toList());
        return result;
    }

}
