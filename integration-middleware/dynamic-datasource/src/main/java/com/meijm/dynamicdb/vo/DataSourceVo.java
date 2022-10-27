package com.meijm.dynamicdb.vo;

import lombok.Data;

@Data
public class DataSourceVo {
    private String poolName;
    private String url;
    private String username;
    private String password;
    private String driverClassName;
}
