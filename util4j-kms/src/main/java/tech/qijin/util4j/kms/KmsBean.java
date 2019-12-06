package tech.qijin.util4j.kms;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.qijin.util4j.kms.config.KmsProperties;
import tech.qijin.util4j.utils.FileUtil;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

/**
 * @author michealyang
 * @date 2019-12-05
 * @relax: 开始眼保健操 ←_← ↓_↓ →_→ ↑_↑
 */
@Slf4j
@Component
public class KmsBean {
    private static final String SECRET_ID = "secretId";
    private static final String SECRET_KEY = "secretKey";

    @Autowired
    private KmsProperties kmsProperties;

    private Optional<Properties> propertiesOpt;

    @PostConstruct
    void init() {
        if (StringUtils.isBlank(kmsProperties.getFilePath())
                || StringUtils.isBlank(kmsProperties.getFileName())) {
            log.error("invalid kms config | kmsFilePath={}, kmsFileName={}", kmsProperties.getFilePath(), kmsProperties.getFileName());
            return;
        }

        try {
            String fullPath = kmsProperties.getFilePath().endsWith("/")
                    ? kmsProperties.getFilePath() + kmsProperties.getFileName()
                    : kmsProperties.getFilePath() + "/" + kmsProperties.getFileName();
            propertiesOpt = FileUtil.readPropertiesFromPath(fullPath);
            if (!propertiesOpt.isPresent()) {
                log.error("failed to load kms properties | fullPath={}", fullPath);
                return;
            }
        } catch (IOException e) {
            log.error("read kms properties exception | e={}", e);
        }
    }

    public Optional<String> getSecretId(String prefix) {
        return propertiesOpt.map(properties -> Optional.ofNullable(properties.getProperty(getPropertiesKey(prefix, SECRET_ID))))
                .orElse(Optional.empty());
    }

    public Optional<String> getSecretKey(String prefix) {
        return propertiesOpt.map(properties -> Optional.ofNullable(properties.getProperty(getPropertiesKey(prefix, SECRET_KEY))))
                .orElse(Optional.empty());
    }

    private String getPropertiesKey(String prefix, String key) {
        if (StringUtils.isNotBlank(prefix)) {
            return prefix + "." + key;
        }
        return key;
    }
}
