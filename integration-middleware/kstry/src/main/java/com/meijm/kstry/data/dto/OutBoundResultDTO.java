package com.meijm.kstry.data.dto;

import lombok.Data;
import lombok.experimental.FieldNameConstants;

@Data
@FieldNameConstants
public class OutBoundResultDTO {
    private String orderNo;
    private Integer count;
}
