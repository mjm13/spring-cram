package com.meijm.basis.javase;

import lombok.Data;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import sun.reflect.generics.reflectiveObjects.TypeVariableImpl;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * 泛型反射测试
 */
@Slf4j
public class GenericityClassTest {
    public static void main(String[] args) throws NoSuchMethodException {
        getGenericityByMethod();
    }

    /**
     * 通过类查找对应的带泛型入参的方法
     */
    public static void getGenericityByClass(){
        Class<Temp> clazz = Temp.class;
        // 遍历类对象的所有方法
        for (Method method : clazz.getMethods()) {
            // 获取方法的参数类型
            Type[] parameterTypes = method.getGenericParameterTypes();
            // 判断参数类型是否为 ParameterizedType
            for (int i = 0; i < parameterTypes.length; i++) {
                // 获取参数类型
                Type parameterType = parameterTypes[i];
                // 判断参数类型是否为 ParameterizedType
                if (parameterType instanceof TypeVariableImpl) {
                    log.info("泛型方法:{}-参数类型:{}",method.getName(),parameterType.getTypeName());
                }
            }
        }
    }

    public static void getGenericityByMethod() throws NoSuchMethodException {
        // 获取方法对象
        Method method = GenericityClassTest.class.getMethod("testGenericity", Temp.class);

        // 获取方法的参数类型
        Type[] parameterTypes = method.getGenericParameterTypes();

        // 遍历参数类型，获取每个参数类型的泛型参数名和泛型类型
        for (int i = 0; i < parameterTypes.length; i++) {
            // 获取参数类型
            Type parameterType = parameterTypes[i];
            // 判断参数类型是否为 ParameterizedType
            if (parameterType instanceof ParameterizedType) {
                // 获取泛型参数名
                Type[] actualTypeArguments = ((ParameterizedType) parameterType).getActualTypeArguments();
                // 获取泛型类型
                log.info("泛型具体类型:{}",actualTypeArguments[0].getTypeName());
            }
        }
    }

    public void testGenericity(Temp<SubTemp> temp) {
        System.out.println("111");
    }
}

@Data
class Temp<T> {
    private T data;
    private String str;
    private List<String> list;
    private Map<String,String> map;
}

@Data
class SubTemp {
    private String subTempStr;
}
