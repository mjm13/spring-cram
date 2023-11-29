package com.meijm.jacksoncrypto.model;

import com.codingrodent.jackson.crypto.Encrypt;
import com.meijm.jacksoncrypto.config.MyJacksonAnnotation;
import lombok.Data;

@Data
public class User {
    private String name;
    @MyJacksonAnnotation
    private String pwd;
    private String address;
}
