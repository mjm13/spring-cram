package com.meijm.elasticsearch.esrepository;

import com.meijm.elasticsearch.entity.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ProductRespository extends ElasticsearchRepository<Product, Integer> {
}
