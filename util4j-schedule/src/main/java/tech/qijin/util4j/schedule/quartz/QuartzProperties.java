package tech.qijin.util4j.schedule.quartz;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author michealyang
 * @date 2018/11/26
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@Data
@Component
@ConfigurationProperties(prefix = "quartz.datasource")
@PropertySource("classpath:quartz.properties")
public class QuartzProperties {
    private String driverClassName;
    private String url;
    private String userName;
    private String password;
}
