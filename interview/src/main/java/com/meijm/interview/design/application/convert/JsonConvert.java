package com.meijm.interview.design.application.convert;

public class JsonConvert implements BaseConvert<PersonA> {
    @Override
    public PersonA convert(Object obj) {
        //.....业务逻辑
        PersonA person = new PersonA("Json", 0);
        return person;
    }

}
