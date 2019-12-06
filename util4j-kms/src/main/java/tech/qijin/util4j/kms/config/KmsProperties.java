package tech.qijin.util4j.kms.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author michealyang
 * @date 2019-12-05
 * @relax: 开始眼保健操 ←_← ↓_↓ →_→ ↑_↑
 */
@Data
@ConfigurationProperties(prefix = "kms")
public class KmsProperties {
    // key 管理文件路径
    private String filePath;
    // key 管理文件名称
    private String fileName;
    // 不同key对应的前缀
    private String prefix;
}
