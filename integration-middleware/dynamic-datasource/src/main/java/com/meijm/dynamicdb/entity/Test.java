package com.meijm.dynamicdb.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Test {
    @Id
    private Long id;
    private String name;
}
