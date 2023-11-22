package com.meijm.jacksoncrypto.config;

import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;

public class MyJacksonAnnotationIntrospector extends JacksonAnnotationIntrospector {

  @Override
  public Object findSerializer(Annotated a) {
    if (a.hasAnnotation(MyJacksonAnnotation.class)) {
      MyJsonSerializer serializer = new MyJsonSerializer();
      return serializer;
    }
    return super.findSerializer(a);
  }

  @Override   
  public Object findDeserializer(Annotated a) {
    if (a.hasAnnotation(MyJacksonAnnotation.class)) {
      MyJsonSerializer deserializer = new MyJsonSerializer();
      return deserializer;
    }
    return super.findDeserializer(a);
  }
}