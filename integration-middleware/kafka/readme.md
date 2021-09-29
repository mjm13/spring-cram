# Spring Boot整合Kafka
pom.xml
```xml
        <dependency>
            <groupId>org.springframework.kafka</groupId>
            <artifactId>spring-kafka</artifactId>
        </dependency>
```
## 默认使用
### 配置

prop新增kafka配置
详细配置可参考org.springframework.boot.autoconfigure.kafka.KafkaProperties中属性
生产者,消费者默认序列化都是String格式message

```properties
#kafka默认消费者配置
spring.kafka.consumer.bootstrap-servers=10.111.7.124:9092
spring.kafka.consumer.enable-auto-commit=true
spring.kafka.consumer.auto-offset-reset=earliest
#kafka默认生产者配置
spring.kafka.producer.bootstrap-servers=10.111.7.124:9092
spring.kafka.producer.acks=-1
spring.kafka.client-id=kafka-producer
spring.kafka.producer.batch-size=5
```

### 使用

```java
//生产者
@Resource
private KafkaTemplate kafkaTemplate;

public void send() {
    HashMap<String, String> map = new HashMap<>();
    map.put("sendType","send");
    kafkaTemplate.send("test01", JSONUtil.toJsonStr(map));
}

```

```java
//消费者
@Slf4j
@Component
public class KafkaConsumer {
    @KafkaListener(topics = "test01",groupId = "group01")
    public void listen(String message) {
        log.info("message:{}",message);
    }
}
```

## 使用Json格式化

### 配置

**自定义消费者工厂:** 配置全部消费者参数,包含转换器,使用此项可删除转换器配置2

**转换器配置2:**仅配置json转换器,不需要配置全部消费者参数时可删除自定义消费者配置

> addTrustedPackages("*"):默认Json转换仅信任java.util,java.lang包下类,增加后不进行类型检查

```java
@Configuration
public class KafkaCustomConfig {

    @Value("${spring.kafka.consumer.bootstrap-servers}")
    private String bootstrapServers;

    //自定义消费者工厂
    @Bean("customContainerFactory")
    public ConcurrentKafkaListenerContainerFactory customContainerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "15000");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 5);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "customGroup01");
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        //转换器配置1
        DefaultKafkaConsumerFactory consumerFactory = new DefaultKafkaConsumerFactory(props);
        JsonDeserializer jsonDeserializer = new JsonDeserializer();
        jsonDeserializer.getTypeMapper().addTrustedPackages("*");
        consumerFactory.setValueDeserializer(jsonDeserializer);
        //指定使用DefaultKafkaConsumerFactory
        ConcurrentKafkaListenerContainerFactory factory = new ConcurrentKafkaListenerContainerFactory();
        factory.setConsumerFactory(consumerFactory);
        //设置可批量拉取消息消费，拉取数量一次3，看需求设置
        factory.setConcurrency(3);
        factory.setBatchListener(true);
        return factory;

    }

    //转换器配置2
    @Bean
    public RecordMessageConverter converter() {
        ByteArrayJsonMessageConverter converter = new ByteArrayJsonMessageConverter();
        DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();
        typeMapper.setTypePrecedence(Jackson2JavaTypeMapper.TypePrecedence.TYPE_ID);
        typeMapper.addTrustedPackages("*");
        converter.setTypeMapper(typeMapper);
        return converter;
    }

    /**
     * 不使用spring boot的KafkaAutoConfiguration默认方式创建的KafkaTemplate，重新定义
     * 与默认配置只能存在一个
     * @return
     */
    @Bean("custiomKafkaTemplate")
    public KafkaTemplate custiomKafkaTemplate() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        //0 producer不等待broker同步完成的确认，继续发送下一条(批)信息
        //1 producer要等待leader成功收到数据并得到确认，才发送下一条message。
        //-1 producer得到follwer确认，才发送下一条数据
        props.put(ProducerConfig.ACKS_CONFIG, "1");
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 5);
        props.put(ProducerConfig.LINGER_MS_CONFIG, 500);
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        DefaultKafkaProducerFactory produceFactory = new DefaultKafkaProducerFactory(props);
        return new KafkaTemplate(produceFactory);
    }
}
```

### 使用

vo

```java
@Data
public class TestVo {
    private String key;
    private String value;
}
```

生产者

```java
//注入自定义KafkaTemplate
@Resource(name = "custiomKafkaTemplate")
private KafkaTemplate custiomKafkaTemplate;

public void customSend() {
    TestVo vo = new TestVo();
    vo.setKey("sendType");
    vo.setValue("send");
    custiomKafkaTemplate.send("custom04", vo);
}
```

消费者

containerFactory:指定自定义消费者工厂beanName

```java
@Slf4j
@Component
public class KafkaConsumer {
    //自定义消费者
	@KafkaListener(topics = "custom04",containerFactory = "customContainerFactory")
    public void customListen(TestVo message) {
        log.info("message:{}",message.toString());
    }
    //仅自定义转换器
	@KafkaListener(topics = "custom04",groupId = "custom04Group")
    public void customListen(TestVo message) {
        log.info("message:{}",message.toString());
    }
}
```





## 参考资料

https://github.com/spring-projects/spring-kafka/tree/master/samples

https://blog.csdn.net/u012045045/article/details/111034500

https://www.tutorialspoint.com/spring_boot/spring_boot_apache_kafka.htm