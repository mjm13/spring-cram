package com.meijm.alibaba.transfer.vo;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
public class Account {

    private Long id;

    private String cardNum;

    private BigDecimal money;
}
