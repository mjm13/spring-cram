package com.meijm.jacksoncrypto.config;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.Annotated;

import java.io.IOException;

public class MyJsonDeserializer extends JsonDeserializer<Object> {

    private Annotated annotated;
    private ObjectMapper objectMapper = new ObjectMapper();

    public MyJsonDeserializer(Annotated annotated) {
        this.annotated = annotated;
    }

    @Override
    public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        return objectMapper.readValue("\"" + p.getText() + "\"", annotated.getType());
    }

}
