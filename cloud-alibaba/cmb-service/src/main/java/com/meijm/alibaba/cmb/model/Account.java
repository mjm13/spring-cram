package com.meijm.alibaba.cmb.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Table(name = "cmb_account")
@Entity
@Data
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cardNum;

    private BigDecimal money;
}
