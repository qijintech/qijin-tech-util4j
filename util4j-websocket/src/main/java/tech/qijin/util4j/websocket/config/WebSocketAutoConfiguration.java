package tech.qijin.util4j.websocket.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author michealyang
 * @date 2019/1/8
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@Configuration
@Import({WebSocketSpiConfiguration.class, WebSocketPropertiesConfig.class})
@EnableConfigurationProperties(WebSocketProperties.class)
@ComponentScan(basePackages = "tech.qijin.util4j.websocket")
public class WebSocketAutoConfiguration {
}
