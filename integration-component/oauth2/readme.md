# Spring OAuth2  学习整理
## [OAuth2 介绍](http://www.ruanyifeng.com/blog/2014/05/oauth_2_0.html)
OAuth2是目前最流行的授权机制，用来授权第三方应用，获取用户数据。

博客提供的流程图中有一点需要注意的是C步骤是用户参与完成验证,验证之后Client拿到对应的Access Token 再进行后续操作.这时Client可以是第三方服务器或者浏览器js存储.
OAuth运行流程
[![](http://www.ruanyifeng.com/blogimg/asset/2014/bg2014051203.png)](http://www.ruanyifeng.com/blogimg/asset/2014/bg2014051203.png)

## 说明

[spring官方发布了blog](https://spring.io/blog/2020/04/15/announcing-the-spring-authorization-server) ,最新版的SpringSecurity已经不支持创建一个授权服务器,转而提供一个标准的授权服务..有种还没上车车就开走了的感觉..
   下面的代码是一个单项目,包含了授权服务,资源服务.使用mybatis-plus自定义了用户信息查询和客户端信息查询.

## 配置
``` xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-oauth2</artifactId>
        </dependency>
```

``` java
@Configuration
//资源服务配置
@EnableResourceServer
//认证服务配置
@EnableAuthorizationServer
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private SysClientDetailsService sysClientDetailsService;


    /**
     * 定义授权和令牌端点以及令牌服务
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                // 请求方式
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
                // 可重写TokenEnhancer 自定生成令牌规则
                .tokenEnhancer(endpoints.getTokenEnhancer())
                // 用户账号密码认证
                .userDetailsService(userDetailsService)
                // 指定认证管理器
                .authenticationManager(authenticationManager)
                // 是否重复使用 refresh_token
                .reuseRefreshTokens(false);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
        //是否允许客户端使用form参数提交,不开启则使用Authorization 要加在请求头中，格式为 Basic 空格 base64(clientId:clientSecret)
        oauthServer.allowFormAuthenticationForClients();
    }

    /**
     * 配置客户端详情
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(sysClientDetailsService);
    }

}
```
# 使用流程
1.获取token

http://localhost:8080/oauth/token?username=admin&password=admin123&code=10&uuid=eb36f9f5946a456b8e24b2491c3db429&client_id=ruoyi&client_secret=123456&grant_type=password&scope=server

2.使用access_token访问/index

http://localhost:8080/index

Authorization:bearer xxxx

其中xxxx为步骤1获取的access_token


3.使用refresh_token获取新的access_token

http://localhost:8080/oauth/token?grant_type=refresh_token&refresh_token=e3b3fc9d-2596-4977-b066-a4b01a2b48ae&client_id=ruoyi&client_secret=123456

# 例子源码
源码为ruoyi2.0版本抽出部分代码构建,新版本ruoyi已去掉oauth2的认证模式

https://gitee.com/MeiJM/spring-cram/tree/master/oauth2

# 参考资料
https://www.cnblogs.com/fengzheng/p/11724625.html

https://github.com/spring-projects/spring-security/wiki/OAuth-2.0-Migration-Guide

http://www.ruanyifeng.com/blog/2014/05/oauth_2_0.html

http://www.ruanyifeng.com/blog/2019/04/oauth_design.html

https://gitee.com/y_project/RuoYi