## 作用

web相关的类和配置

### filter

放置各种过滤器

**建议**

* 不要在`Filter`的实现类上添加@Componnet注解

添加这个注解后，Filter会被自动注册，不利于管理。

* 在`FilterRegistration`类中注册`Filter`

```java
@Bean
public FilterRegistrationBean<RequestFilter> requestFilterBean() {
    FilterRegistrationBean registrationBean = new FilterRegistrationBean();
    RequestFilter requestFilter = new RequestFilter();
    registrationBean.setFilter(requestFilter);
    registrationBean.setOrder(1);
    return registrationBean;
}
```

* 在`FilterRegistration`中设置`Filter`的顺序，不要在`Filter`的实现类中使用`@Order`注解

```java
//不推荐：
@Order(1)
public class RequestFilter implements Filter {
    ...
}

//推荐：
@Configuration
public class FilterRegistration {
    @Bean
    public FilterRegistrationBean<RequestFilter> requestFilterBean() {
        RequestFilter requestFilter = new RequestFilter();
        registrationBean.setOrder(1);
    }
}
```