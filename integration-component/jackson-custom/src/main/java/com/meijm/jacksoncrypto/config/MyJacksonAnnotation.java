package com.meijm.jacksoncrypto.config;

import com.fasterxml.jackson.annotation.JacksonAnnotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@JacksonAnnotation
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyJacksonAnnotation {

}