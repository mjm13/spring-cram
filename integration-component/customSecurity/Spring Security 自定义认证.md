# 角色说明
* CustomAuthenticationFilter:构建Token类并交给AuthenticationProvider进行验证,继承自AbstractAuthenticationProcessingFilter,AbstractAuthenticationProcessingFilter为UsernamePasswordAuthenticationFilter的父类,封装了登陆过程用到的常用内容及方法.也可以不继承此类完核心功能即可.
* CustomAuthenticationProvider:对支持的token进行校验与shiro中Realm类似,区别是Security将授权与认证合并了.
* CustomAuthenticationToken:自定义token,存储自定义内容,程序中使用SecurityContextHolder.getContext().getAuthentication()来获取

# java配置
## 过滤器配置
``` java
public class CustomAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public CustomAuthenticationFilter() {
        super(new AntPathRequestMatcher("/custom/**"));
        //
        setContinueChainBeforeSuccessfulAuthentication(true);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        CustomAuthenticationToken authentication = null;
        try {
            Enumeration<String> headers = request.getHeaders("secretKey");
            String secretKey = headers.nextElement();
            // 通过request中参数构建自定义token,与CustomAuthenticationProvider对应即可
            authentication = new CustomAuthenticationToken(secretKey, secretKey);
            //设置附属信息,sessionid,ip
            setDetails(request, authentication);
            //通过Provider验证token
            authentication = (CustomAuthenticationToken) getAuthenticationManager().authenticate(authentication);
            //
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            throw new AuthenticationServiceException("secretKey认证失败",e);
        }
        return authentication;
    }

    protected void setDetails(HttpServletRequest request,
                              CustomAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }
}
```

**说明**
* 此示例是在请求头中增加参数用于认证,并没有后续的登陆成功处理所以设置了setContinueChainBeforeSuccessfulAuthentication
* setContinueChainBeforeSuccessfulAuthentication设置为true表示认证成功之后进入后续过滤,不走后续的登陆成功处理
* 需要手动将认证结果存入SecurityContextHolder中避免后续拿不到认证信息.



## Provider 配置
``` java
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String secretKey = (String) authentication.getCredentials();
        //自定义校验逻辑
        if(!"123".equals(secretKey)){
            throw new InsufficientAuthenticationException("认证错误");
        }
        Set<String> dbAuthsSet = new HashSet<String>();
        dbAuthsSet.add("customUser");

        Collection<? extends GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(dbAuthsSet.toArray(new String[0]));
        return new CustomAuthenticationToken(secretKey, secretKey, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return CustomAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
```

## SecurityConfig配置

``` java
@Order(2)
@Configuration
public class CustomSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .antMatcher("/custom/**")
                .addFilterBefore(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
                .and().authorizeRequests().antMatchers("/custom/**").authenticated();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(customAuthenticationProvider);
    }

    @Bean
    public CustomAuthenticationFilter customAuthenticationFilter() throws Exception {
        CustomAuthenticationFilter filter = new CustomAuthenticationFilter();
        filter.setAuthenticationManager(super.authenticationManagerBean());
        return filter;
    }

}
```

# 相关代码
https://gitee.com/MeiJM/spring-cram/tree/master/customSecurity 中CustomSecurityConfig相关部分