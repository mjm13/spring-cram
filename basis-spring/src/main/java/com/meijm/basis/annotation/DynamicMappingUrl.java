package com.meijm.basis.annotation;

import cn.hutool.json.JSONUtil;
import com.meijm.basis.dto.PageQueryDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

@ResponseBody
@Slf4j
public class DynamicMappingUrl {

    private Method method;
    
    private Object source;

    public DynamicMappingUrl(Method method,Object source){
        this.method = method;
        this.source = source;
    }

    public String addMapping(Map<String,String> param){
        log.info("in addMapping:{}", JSONUtil.toJsonStr(param));
        param.put("location","DynamicMappingUrl");
        try {
            method.invoke(source,param);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return JSONUtil.toJsonStr(param);
    }
}

