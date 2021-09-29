# 例子简介
将spring-cache,spring-session存储配置为redis,并提供三个例子分别对应cache,session以及redisTemplate的读写操作,仅保留了核心代码,其它配置请参考官方文档.

# 配置
* pom
``` xml
<dependency>
    <groupId>org.springframework.session</groupId>
    <artifactId>spring-session-data-redis</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-cache</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```
* application.properties
```
# 使用redis存储session
spring.session.store-type=redis
# 单位时间秒
server.servlet.session.timeout= 1800
# session存储名称
spring.session.redis.namespace=spring:session
# redis地址配置,
spring.redis.host=localhost
```
* java配置
``` java
@Configuration
@EnableCaching
public class RedisConfig {
    /**
     * 固定方法名称,否则session序列化不会使用json格式
     * org.springframework.session.data.redis.config.annotation.web.http.RedisHttpSessionConfiguration#setDefaultRedisSerializer(org.springframework.data.redis.serializer.RedisSerializer)
     * @return
     */
    @Bean
    public RedisSerializer<Object> springSessionDefaultRedisSerializer() {
        //使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值（默认使用JDK的序列化方式）
        Jackson2JsonRedisSerializer serializer = new Jackson2JsonRedisSerializer(Object.class);
        //序列化配置
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        serializer.setObjectMapper(mapper);
        return serializer;
    }

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory) {
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // 配置序列化
        RedisCacheConfiguration config = RedisCacheConfiguration
                .defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(stringRedisSerializer))
                // 值序列化方式 简单json序列化
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(springSessionDefaultRedisSerializer()))
                //过期时间
                .entryTtl(Duration.ofMinutes(5));

        return RedisCacheManager
                .builder(factory)
                .cacheDefaults(config)
//                .withCacheConfiguration("cacheName",customConfig)
                .build();

    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setValueSerializer(springSessionDefaultRedisSerializer());
        //使用StringRedisSerializer来序列化和反序列化redis的key值
        template.setKeySerializer(new StringRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }

}
```
**session配置说明:** 默认session存储redis使用的是JdkSerializationRedisSerializer,要替换则需要手动注入springSessionDefaultRedisSerializer

**cache配置说明:** 使用withCacheConfiguration可针对缓存名称定制配置

**其它:** 官方提供的json格式序列化有Jackson2JsonRedisSerializer和GenericJackson2JsonRedisSerializer,GenericJackson2JsonRedisSerializer会将类名存储在json字符串中所以可读性更高,但如果序列化bean不规范则会报错,例如没有空构造函数,get,set不全等.

# 相关代码
https://gitee.com/MeiJM/spring-cram/tree/master/redis

# 参考资料
https://docs.spring.io/spring-session/docs/current/reference/html5/#custom-sessionrepository
