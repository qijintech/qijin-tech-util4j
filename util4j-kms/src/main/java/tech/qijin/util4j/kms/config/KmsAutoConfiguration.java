package tech.qijin.util4j.kms.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author michealyang
 * @date 2019-12-05
 * @relax: 开始眼保健操 ←_← ↓_↓ →_→ ↑_↑
 */
@Configuration
@ComponentScan("tech.qijin.util4j.kms")
@EnableConfigurationProperties(KmsProperties.class)
public class KmsAutoConfiguration {
}
