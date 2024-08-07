package com.meijm.jacksoncrypto.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.introspect.Annotated;

import java.io.IOException;

public class MyJsonSerializer extends JsonSerializer<Object> {
    private Annotated annotated;
    public MyJsonSerializer(Annotated annotated){
        this.annotated = annotated;
    }
    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null) {
            gen.writeNull();
        } else {
            
            gen.writeString("字符串 1");
        } 
    }
}
