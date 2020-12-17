package tech.qijin.util4j.metrics;

import com.google.common.collect.Lists;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tech.qijin.util4j.metrics.config.MetricsAutoConfiguration;
import tech.qijin.util4j.metrics.config.MetricsProperties;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class Metrics {
    @Autowired
    private MetricsProperties metricsProperties;
    @Autowired
    private MeterRegistry meterRegistry;

    public void counter(String metricName, String... tags) {
        meterRegistry.counter(metricName, addTags(tags)).increment();
    }

    public void counter(String metricName, int value, String... tags) {
        meterRegistry.counter(metricName, addTags(tags)).increment(value);
    }

    public void timer(String metricName, long milliSeconds, String... tags) {
        meterRegistry.timer(metricName, addTags(tags)).record(milliSeconds, TimeUnit.MILLISECONDS);
    }

    private String[] addTags(String... tags) {
        List<String> t = Lists.newArrayList("group", metricsProperties.getGroup(),
                "ip", MetricsAutoConfiguration.IP);
        if ((tags.length & 0x1) != 0) {
            log.error("tags length must be even. tags={}", tags);
        } else {
            t.addAll(Arrays.asList(tags));
        }
        return t.toArray(new String[0]);
    }
}
