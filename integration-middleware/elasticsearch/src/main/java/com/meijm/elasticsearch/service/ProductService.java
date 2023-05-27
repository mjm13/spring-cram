package com.meijm.elasticsearch.service;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.apifan.common.random.source.AreaSource;
import com.apifan.common.random.source.FinancialSource;
import com.apifan.common.random.source.OtherSource;
import com.meijm.elasticsearch.entity.Product;
import com.meijm.elasticsearch.esrepository.ProductRespository;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.*;
import org.springframework.data.elasticsearch.core.clients.elasticsearch7.ElasticsearchAggregations;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Slf4j
public class ProductService {
    @Autowired
    private ProductRespository productRespository;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;


    public void generateData() {
        //创建索引
        IndexOperations indexOperations = elasticsearchRestTemplate.indexOps(Product.class);
        indexOperations.putMapping(indexOperations.createMapping());

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
        ElasticsearchAggregations aggregationsContainer = (ElasticsearchAggregations) searchHits.getAggregations();
        Aggregations aggregations = aggregationsContainer.aggregations();
        Aggregation aggregation = aggregations.get(name);
        return aggregation.getMetadata();
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
