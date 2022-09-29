package com.meijm.alibaba.transfer.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Table(name = "transfer_log")
@Entity
@Data
public class TransferLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String originBank;
    private String targetBank;

    private String originCardNum;
    private String targetCardNum;

    private BigDecimal transferMoney;
}
