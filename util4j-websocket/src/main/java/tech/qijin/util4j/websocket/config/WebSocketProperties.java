package tech.qijin.util4j.websocket.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author michealyang
 * @date 2019/1/24
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@ConfigurationProperties(prefix = "websocket.netty")
@Data
public class WebSocketProperties {
    private Integer port = 3301;
    private String uri = "/connect";
}
