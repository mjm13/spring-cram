# [Spring Security多入口](https://docs.spring.io/spring-security/site/docs/5.4.1/reference/html5/#multiple-httpsecurity)
官方为了方便演示使用的是一个主类,两个内部类来实现的多入口,下面的例子将其拆分为两个配置类,两个用户方便理解.

# 配置
``` java
@Configuration
public class FormSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().disable()
                .csrf().disable()
                .authorizeRequests().antMatchers("/form/**")
                .hasRole("USER")
                .and()
                .formLogin().successForwardUrl("/form/index");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("user")
                .password(passwordEncoder().encode("user"))
                .roles("USER");
    }
}
```
**说明:**
* 上面的配置为系统指定了user为默认用户,拥有USER权限,
* 指定以form起始的路径需要校验USER权限
* 增加formLogin,指定/form/index为登陆成功指向的页面

``` java
@Order(1)
@Configuration
public class BasicSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .antMatcher("/basic/**")
                .authorizeRequests().anyRequest()
                .hasRole("BASIC")
                .and()
                .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("basic")
                .password(passwordEncoder.encode("basic"))
                .roles("BASIC");
    }
}
```
**说明:**
* 使用@Order(1)指定了加载顺序
* 上面的配置为系统指定了basic为默认用户,拥有BASIC权限,
* 指定以basic起始的路径需要校验BASIC权限
* 增加httpBasic验证

# 注意事项
1. HttpSecurity配置以authorizeRequests为起始表示针对所有请求路径
2. HttpSecurity配置以antMatcher("/basic/**")为新增一个入口
3. FormSecurityConfig 未写@Order继承WebSecurityConfigurerAdapter中注解序号为100
4. 由于formLogin会增加默认登陆页过滤器/login所以不能使用其它路径作为起始,否则会导致默认登录页不生效
5. 如果authorizeRequests加载顺序靠前会导致后续配置的antMatcher对应的路径失效.





# 相关代码
https://gitee.com/MeiJM/spring-cram/tree/master/customSecurity

# 参考资料
https://docs.spring.io/spring-security/site/docs/5.4.1/reference/html5/#multiple-httpsecurity

https://www.baeldung.com/spring-security-multiple-entry-points

https://github.com/spring-projects/spring-security/issues/5593

https://github.com/mageddo/java-examples/blob/6a7dd2b/spring-security/basic-and-form-auth-together/src/main/java/com/mageddo/springsecurity/SecurityConfig.java

