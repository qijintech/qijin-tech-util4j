package tech.qijin.util4j.rpc.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author michealyang
 * @date 2020-02-26
 * @relax: 开始眼保健操 ←_← ↓_↓ →_→ ↑_↑
 */
@ConfigurationProperties(prefix = "util4j.rpc.http")
public class RpcHttpProperties {
    private Integer readTimeout;
    private Integer retryTimes;
}
