package tech.qijin.util4j.schedule.quartz.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.quartz.QuartzJobBean;

import javax.sql.DataSource;

/**
 * @author michealyang
 * @date 2018/12/12
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@Configuration
@EnableConfigurationProperties(QuartzProperties.class)
@Import(QuartzPropertiesConfiguration.class)
@ConditionalOnClass({QuartzJobBean.class})
public class QuartzAutoConfiguration {

    @Autowired
    private QuartzProperties quartzProperties;

    @Bean
    @QuartzDataSource
    @ConfigurationProperties("spring.quartz.datasource")
    public DataSource quartzDataSource(){
        return DruidDataSourceBuilder.create().build();
    }
}
