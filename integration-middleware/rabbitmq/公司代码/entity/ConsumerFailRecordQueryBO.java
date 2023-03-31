package com.prolog.rdc.outbound.mq.entity;

import lombok.Data;

import java.util.List;

@Data
public class ConsumerFailRecordQueryBO {

    private List<String> ids;

    private String businessType;

    private String businessNo;

}
