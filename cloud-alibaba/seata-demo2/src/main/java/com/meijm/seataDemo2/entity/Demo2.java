package com.meijm.seataDemo2.entity;





import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.persistence.Table;

@Data
@Table(name="demo_1")
public class Demo2 {
    @Id
    private Long id;
    private String mark;
}
