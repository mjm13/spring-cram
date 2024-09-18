package com.meijm.kstry.data.dto;

import lombok.Data;
import lombok.experimental.FieldNameConstants;

@Data
@FieldNameConstants
public class OutBoundParamDTO {
    private String orderNo;
    private Integer count;
}
