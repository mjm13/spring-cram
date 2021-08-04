package com.meijm.interview.design.application.convert;

public class XmlConvert implements BaseConvert<PersonA> {


    @Override
    public PersonA convert(Object obj) {

        //.....业务逻辑
        PersonA person = new PersonA("Xml", 0);
        return person;
    }

}
