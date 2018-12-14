package tech.qijin.util4j.schedule.config;

import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.*;
import tech.qijin.util4j.schedule.IScheduler;
import tech.qijin.util4j.schedule.quartz.config.QuartzAutoConfiguration;

/**
 * @author michealyang
 * @date 2018/12/12
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@Configuration
public class ScheduleAutoConfiguration {

    @Configuration
    @ConditionalOnProperty(prefix = "schedule", name = "type", matchIfMissing = false, havingValue = "quartz")
    @ConditionalOnMissingBean({IScheduler.class})
    @Import(QuartzAutoConfiguration.class)
    protected static class QuartzConfiguration {
    }
}
