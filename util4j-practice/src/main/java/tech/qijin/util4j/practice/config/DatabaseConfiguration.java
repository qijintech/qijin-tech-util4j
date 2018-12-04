package tech.qijin.util4j.practice.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;


/**
 * 参考文章：https://medium.com/@d.lopez.j/spring-boot-mybatis-multiple-datasources-and-multiple-mappers-all-together-holding-hands-be74673c6a9f
 *
 * @author michealyang
 * @date 2018/11/28
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@Configuration
public class DatabaseConfiguration {
    public static final String PRIMARY_DATASOURCE = "primary";

    @Bean(name = PRIMARY_DATASOURCE)
    @ConfigurationProperties(prefix = "spring.datasource")
    @Primary
    public DataSource dataSourceOne() {
        // Filled up with the properties specified about thanks to Spring Boot black magic
        return new DruidDataSource();
    }
}
