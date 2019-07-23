package tech.qijin.util4j.schedule.quartz.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * @author michealyang
 * @date 2018/11/26
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@Data
@ConfigurationProperties(prefix = "spring.quartz.datasource")
public class QuartzProperties {
    private String driverClassName;
    private String url;
    private String userName;
    private String password;
}
