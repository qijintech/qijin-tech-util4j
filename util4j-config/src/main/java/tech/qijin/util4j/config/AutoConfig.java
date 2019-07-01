package tech.qijin.util4j.config;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import tech.qijin.util4j.lang.constant.Const;
import tech.qijin.util4j.utils.FileUtil;
import tech.qijin.util4j.utils.LogFormat;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author michealyang
 * @date 2019/4/9
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@Slf4j
@Service
public class AutoConfig {
    private boolean isDev = false;
    private boolean isTest = false;
    private boolean isProd = false;

    private static final String CONFIG_THREAD_NAME = "SCHEDULED_CONFIG_THREAD";

    private Properties properties = new Properties();

    @Autowired
    private Environment env;
    @Autowired
    private Config config;


    @PostConstruct
    public void init() {
        List<String> activeProfiles = Lists.newArrayList(env.getActiveProfiles());
        if (CollectionUtils.isEmpty(activeProfiles)) {
            log.warn(LogFormat.builder().message("empty active profiles").build());
            return;
        }
        if (activeProfiles.contains(Const.DEV)) {
            isDev = true;
        } else if (activeProfiles.contains(Const.TEST)) {
            isTest = true;
        } else if (activeProfiles.contains(Const.PROD)) {
            isProd = true;
        } else {
            isDev = true;
        }
        Executors.newSingleThreadScheduledExecutor(r -> new Thread(r, CONFIG_THREAD_NAME))
                .scheduleWithFixedDelay(() -> loadProperties(), 0, 10, TimeUnit.SECONDS);
    }

    public void loadProperties() {
        log.info(LogFormat.builder().message("start to load properties").build());
        String configFile = "config-center-dev.properties";
        try {
            if (isTest) {
                configFile = "config-center-test.properties";
            } else if (isProd) {
                configFile = "config-center-prod.properties";
            }
            Optional<Properties> propertiesOpt = FileUtil.readPropertiesFromPath(config.configPath() + config.module() + "/" + configFile);
            if (!propertiesOpt.isPresent()) {
                propertiesOpt = FileUtil.readPropertiesFromClasspath(configFile);
            }
            if (propertiesOpt.isPresent()) {
                properties = propertiesOpt.get();
                return;
            }
        } catch (Exception e) {
            log.error(LogFormat.builder().message("load properties exception").build(), e);
        }
    }

    public Properties getProperties() {
        return this.properties;
    }
}
