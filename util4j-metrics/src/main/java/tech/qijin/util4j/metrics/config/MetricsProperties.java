package tech.qijin.util4j.metrics.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "metrics")
public class MetricsProperties {
    private String group = "default";
    private String username;
    private String password;
}
