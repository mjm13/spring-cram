package com.meijm.interview.design.application.convert;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class   ConvertRegistry {
    private Map<String, BaseConvert> registers;
    private static ConvertRegistry instance = new ConvertRegistry();
    private ConvertRegistry(){
        initRegistry();
    }
    public static ConvertRegistry getInstance(){
        return instance;
    }

    public <T> T convert(String type,Object object){
        BaseConvert<T> convert = registers.get(type);
        return convert.convert(object);
    }


    private void initRegistry(){
        registers = new ConcurrentHashMap<>();
        registers.put("json",new JsonConvert());
        registers.put("xml",new XmlConvert());
        registers.put("str",new StrConvert());
    }

}
