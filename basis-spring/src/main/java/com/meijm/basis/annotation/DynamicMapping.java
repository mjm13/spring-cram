package com.meijm.basis.annotation;

import java.lang.annotation.*;


/**
 * 添加缓存注解，
 * prefix+businessKey+key为rediskey 使用:拼接
 * 找到缓存则直接返回，未找到则执行方法后缓存至redis中
 *
 * @author MeiJM
 * @date 2023/7/17
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface DynamicMapping {
    /**
     * key前缀
     *
     * @return
     */
    String prefix() default "spring_application_name";

    /**
     * 文件名称
     *
     * @return
     */
    String fileName() default "";
}
