
# [dynamic-datasource-spring-boot-starter简介](https://gitee.com/baomidou/dynamic-datasource-spring-boot-starter)
dynamic-datasource-spring-boot-starter是mybatis-plus作者baomidou开源的一个基于springboot的快速集成多数据源的启动器，支持通过注解切换数据源，仅关注数据源切换，和mybatis-plus/jpa都可进行集成，免费文档中介绍了基于注解和配置实现的数据源切换，我这里是查看源码后总结的代码。

> 官方文档目前是收费的，提供了很多高级用法相关的[文档](https://www.kancloud.cn/tracy5546/dynamic-datasource/2264611)。，如果项目需要建议支持开源，还可以加入专属QQ群。

# 例子说明
下面的例子是使用jpa+dynamic-datasource，实现切库查询不同的数据库。
## 配置
* pom中增加依赖

```xml
    <dependencies>
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>dynamic-datasource-spring-boot-starter</artifactId>
            <version>3.5.2</version>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
    </dependencies>

```

application配置文件中配置主库

```properties
spring.datasource.dynamic.primary=demo
spring.datasource.dynamic.datasource.demo.url= jdbc:mysql://localhost:3306/demo?useSSL=false&serverTimezone=CTT&useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true
spring.datasource.dynamic.datasource.demo.username=root
spring.datasource.dynamic.datasource.demo..password=root
spring.datasource.dynamic.datasource.demo..driver-class-name=com.mysql.cj.jdbc.Driver
```
> 主库默认为master，需要改名指定primary属性即可

## 核心代码

```java
    //动态数据源
    @Autowired
    private DynamicRoutingDataSource drds;
    //数据源创建器
    @Autowired
    private BasicDataSourceCreator dataSourceCreator;
    @Autowired
    private TestDao testDao;
    //创建数据源
    public void createDataSource(DataSourceVo vo) {
        DataSourceProperty dsp = new DataSourceProperty();
        dsp.setPoolName(vo.getPoolName());//链接池名称
        dsp.setUrl(vo.getUrl());//数据库连接
        dsp.setUsername(vo.getUsername());//用户名
        dsp.setPassword(vo.getPassword());//密码
        dsp.setDriverClassName(vo.getDriverClassName());//驱动
        //创建数据源并添加到系统中管理
        DataSource dataSource = dataSourceCreator.createDataSource(dsp);
        drds.addDataSource(vo.getPoolName(), dataSource);
    }
    
    public List<Test> changeSourceAndQuery(String poolName){
        List<Test> result = new ArrayList<>();
        DynamicDataSourceContextHolder.push(poolName);//数据源名称
        try {
            result =  testDao.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DynamicDataSourceContextHolder.poll();
        }
        return result;
    }
    
    public Set<String> getDataSourceKeys() {
        return drds.getDataSources().keySet();
    }
```
## 说明
* createDataSource：通过传入参数来添加数据源到内存中
* getDataSourceKeys：用于检查数据源是否添加成功
* changeSourceAndQuery：切换当前数据源，并执行查询，执行完成后还原默认数据源

核心逻辑是维护了一个线程级栈其中存储了加入的数据源名称，和dataSourceMap配合使用实现方法的数据源嵌套切换。

>这里缺失了TestDao，这个dao是使用jpa接口实现的单表查询，主要是用来区分切换数据源是否成功