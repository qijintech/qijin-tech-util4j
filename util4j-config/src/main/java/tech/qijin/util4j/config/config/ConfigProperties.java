package tech.qijin.util4j.config.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
 * @author michealyang
 * @date 2019/3/8
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@Data
@ConfigurationProperties(prefix = "config")
public class ConfigProperties {
    //配置域名
    private String host;

    /**
     * 配置文件路径
     */
    private String configPath;
}
