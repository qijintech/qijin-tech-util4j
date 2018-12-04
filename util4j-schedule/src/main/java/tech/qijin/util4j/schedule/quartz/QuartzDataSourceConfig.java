package tech.qijin.util4j.schedule.quartz;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author michealyang
 * @date 2018/11/26
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@Configuration
public class QuartzDataSourceConfig {

    @Autowired
    private QuartzProperties quartzProperties;

    @Bean
    @QuartzDataSource
    public DataSource quartzDataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(quartzProperties.getDriverClassName());
        dataSource.setUrl(quartzProperties.getUrl());
        dataSource.setUsername(quartzProperties.getUserName());
        dataSource.setPassword(quartzProperties.getPassword());
        return dataSource;
    }
}
