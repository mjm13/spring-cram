package com.meijm.elasticsearch.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Document(indexName = "product")
public class Product {
    @Id
    private Long id;
    @Field(type = FieldType.Text, analyzer = "ik_smart", searchAnalyzer = "ik_max_word")
    private String name;
    @Field(type = FieldType.Keyword)
    private String specifications;
    @Field(type = FieldType.Keyword)
    private String origin;
    @Field(type = FieldType.Keyword)
    private String manufactor;
    @Field(type = FieldType.Keyword)
    private String company;
}
