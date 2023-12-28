package com.meijm.basis.bean;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Map;

//@Component
@Validated
@Data
@PropertySource("classpath:complex.yml")
@ConfigurationProperties(prefix = "custom.complex")
public class ComplexProp {
    private String test;
    private List<Map> mapList;
    private Map<String, Map> mapMap;
}
