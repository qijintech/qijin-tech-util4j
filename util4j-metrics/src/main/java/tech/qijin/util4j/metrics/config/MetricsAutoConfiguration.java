package tech.qijin.util4j.metrics.config;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.exporter.BasicAuthHttpConnectionFactory;
import io.prometheus.client.exporter.PushGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.autoconfigure.metrics.export.prometheus.PrometheusProperties;
import org.springframework.boot.actuate.metrics.export.prometheus.PrometheusPushGatewayManager;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Map;

/**
 * @author michealyang
 * @date 2019/3/13
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@Slf4j
@Configuration
@Import(MetricsPropertiesConfiguration.class)
@ComponentScan("tech.qijin.util4j.metrics")
@EnableConfigurationProperties(MetricsProperties.class)
public class MetricsAutoConfiguration {
    public static String IP = "0.0.0.0";

    @PostConstruct
    public void init() {
        try {
            InetAddress addr = InetAddress.getLocalHost();
            IP = addr.getHostAddress();
        } catch (Exception e) {
            log.error("get localhost error", e);
        }
    }

    @Bean
    @Primary
    public PrometheusPushGatewayManager prometheusPushGatewayManager(MetricsProperties metricsProperties,
                                                                     PrometheusProperties prometheusProperties,
                                                                     CollectorRegistry collectorRegistry) throws MalformedURLException {
        PrometheusProperties.Pushgateway properties = prometheusProperties.getPushgateway();
        Duration pushRate = properties.getPushRate();
        String job = this.getJob(metricsProperties);
        Map<String, String> groupingKey = properties.getGroupingKey();
        groupingKey.put("instance", IP);
        PrometheusPushGatewayManager.ShutdownOperation shutdownOperation = properties.getShutdownOperation();
        return new PrometheusPushGatewayManager(this.getPushGateway(metricsProperties, properties.getBaseUrl()), collectorRegistry, pushRate, job, groupingKey, shutdownOperation);
    }

    private PushGateway getPushGateway(MetricsProperties metricsProperties, String url) throws MalformedURLException {
        PushGateway pushGateway = new PushGateway(new URL(url));
        pushGateway.setConnectionFactory(new BasicAuthHttpConnectionFactory(metricsProperties.getUsername(), metricsProperties.getPassword()));
        return pushGateway;
    }

    private String getJob(MetricsProperties metricsProperties) {
        return metricsProperties.getGroup();
    }
}
