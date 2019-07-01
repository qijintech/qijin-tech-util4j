package tech.qijin.util4j.practice.conditional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Map;

/**
 * @author michealyang
 * @date 2019/3/27
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
public class MyCondition extends SpringBootCondition {
    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Map<String, Object> annotationAttributes = metadata.getAnnotationAttributes(MyConditionalOnProperty.class.getName());
        String prefix = (String) annotationAttributes.get("prefix");
        String name = (String) annotationAttributes.get("name");
        String value = (String) annotationAttributes.get("value");
        if (StringUtils.isBlank(prefix) || StringUtils.isBlank(name)) {
            return new ConditionOutcome(false, "lack of attributes");
        }

        //获取environment中的值
        String property = context.getEnvironment().getProperty(prefix + "." + name);
        if (value.equals(property)) {
            //如果environment中的值与指定的value一致，则返回true
            return new ConditionOutcome(true, "ok");
        }
        return new ConditionOutcome(false, "error");
    }
}
