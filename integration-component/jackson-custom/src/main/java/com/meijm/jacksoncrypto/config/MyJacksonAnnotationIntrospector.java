package com.meijm.jacksoncrypto.config;

import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;

public class MyJacksonAnnotationIntrospector extends JacksonAnnotationIntrospector {

  @Override
  public Object findSerializer(Annotated annotated) {
    if (annotated.hasAnnotation(MyJacksonAnnotation.class)) {
      return new MyJsonSerializer(annotated);
    }
    return super.findSerializer(annotated);
  }

  @Override   
  public Object findDeserializer(Annotated annotated) {
    if (annotated.hasAnnotation(MyJacksonAnnotation.class)) {
      return new MyJsonDeserializer(annotated);
    }
    return super.findDeserializer(annotated);
  }
}