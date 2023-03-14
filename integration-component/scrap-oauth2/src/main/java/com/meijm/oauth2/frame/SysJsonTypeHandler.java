package com.meijm.oauth2.frame;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.extension.handlers.AbstractJsonTypeHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.io.IOException;

@Slf4j
@MappedTypes({Object.class})
@MappedJdbcTypes({JdbcType.VARCHAR})
public class SysJsonTypeHandler<T> extends AbstractJsonTypeHandler<Object> {

    private static ObjectMapper objectMapper = new ObjectMapper();
    private Class<?> type;

    public SysJsonTypeHandler(Class<?> type) {
        if (log.isTraceEnabled()) {
            log.info("JacksonTypeHandler(" + type + ")");
        }

        this.type = type;
    }

    @Override
    protected Object parse(String json) {
        Object result = null;
        try {
            if (StrUtil.isNotBlank(json)) {
                result = objectMapper.readValue(json, this.type);
            }
        } catch (IOException var3) {
            throw new RuntimeException(var3);
        }
        return result;
    }

    @Override
    protected String toJson(Object obj) {
        String result = null;
        try {
            if (obj != null) {
                result = objectMapper.writeValueAsString(obj);
            }
        } catch (JsonProcessingException var3) {
            throw new RuntimeException(var3);
        }
        return result;
    }

    public static void setObjectMapper(ObjectMapper objectMapper) {
        Assert.notNull(objectMapper, "ObjectMapper should not be null", new Object[0]);
        SysJsonTypeHandler.objectMapper = objectMapper;
    }
}
