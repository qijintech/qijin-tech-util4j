package tech.qijin.util4j.practice.conditional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author michealyang
 * @date 2019/3/27
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@Configuration
public class ConditionalConfig {
    @Configuration
    @MyConditionalOnProperty(prefix = "conditional", name = "test1", value = "true")
    public class Inner {
        @Bean
        public ConditionalTestBean1 testBean1() {
            return new ConditionalTestBean1();
        }

        @Bean
        public ConditionalTestBean2 testBean2() {
            return new ConditionalTestBean2();
        }
    }

}
