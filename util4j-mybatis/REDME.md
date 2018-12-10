
## AbstractTenantInterceptor
对多租户的抽象支持。“租户”可以用来区分环境，区分是否有效等等。

如，EnvInterceptor和ValidInterceptor均是继承自这个类，用以实现自动设置env和valid字段的值。

### 使用方法

Step1: extends `AbstractTenantInterceptor`类
```java
public class EnvInterceptor extends AbstractTenantInterceptor<Integer> {
    @Override
    public Integer getTenantValue() {
    }
}
```

Step2: 实现`getTenantValue()`方法。

这个方法的返回值即为对应的数据库字段要设置的值。

如`EnvInterceptor`中这个值是通过`EnvUtil`获取的：

```java
public class EnvInterceptor extends AbstractTenantInterceptor<Integer> {
    @Override
    public Integer getTenantValue() {
        return EnvUtil.getEnv().value();
    }
}
```

Step3: 添加`@Intercepts`注解

这个注解是固定的，无需修改

```java
@Intercepts(value = {
        @Signature(type = Executor.class, method = "update",
                args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = "query",
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class EnvInterceptor extends AbstractTenantInterceptor<Integer> {}
```

Step4: 配置mybatis拦截器

在mybatis配置文件(如sqlMapConfig.xml)中，添加拦截器

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <plugins>
        <plugin interceptor="tech.qijin.util4j.mybatis.interceptor.EnvInterceptor">
            <property name="enable" value="true"></property>
            <property name="tenantColumnName" value="env"></property>
            <property name="includedTables" value="user"></property>
        </plugin>
    </plugins>

</configuration>
```
### 配置项

* `enable`: 是否开启拦截。true表示开启，false表示不开启，那么这个Interceptor配置也没啥用
* `tenantColumnName`: 要自动设置的数据库column名字。
* `includedTables`: 要自动设置的数据库table列表。因为有些table有这个租户字段，有些没有，所以这里需要设置一下哪些table需要自动设置这个数据库值。用逗号分隔，如`user, account`
* `excludedTables`: 与`includedTables`相对应。当需要自动设置的table很多时，就设置`excludedTables`；相反，当无需自动设置的table很多时，则设置`includedTables`，怎么写得少，怎么来。

注：如果`includedTables`和`excludedTables`都没设置，则默认所有的table都需要自动设置

### 多种租户配置示例

怎么同时支持env和valid均自动设置呢？设置两个拦截器就行了：

```xml
<plugin interceptor="tech.qijin.util4j.mybatis.interceptor.ValidInterceptor">
    <property name="enable" value="true"></property>
    <property name="tenantColumnName" value="valid"></property>
    <property name="includedTables" value="user"></property>
</plugin>
<plugin interceptor="tech.qijin.util4j.mybatis.interceptor.EnvInterceptor">
    <property name="enable" value="true"></property>
    <property name="tenantColumnName" value="env"></property>
    <property name="includedTables" value="user"></property>
</plugin>
```