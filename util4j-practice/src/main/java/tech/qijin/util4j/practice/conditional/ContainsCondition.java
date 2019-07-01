package tech.qijin.util4j.practice.conditional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author michealyang
 * @date 2019/3/27
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
public class ContainsCondition extends SpringBootCondition {
    private final String delimiter = ",";

    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Map<String, Object> annotationAttributes = metadata.getAnnotationAttributes(ContainsConditional.class.getName());
        String prefix = (String) annotationAttributes.get("prefix");
        String name = (String) annotationAttributes.get("name");
        String value = (String) annotationAttributes.get("value");
        if (StringUtils.isBlank(prefix)
                || StringUtils.isBlank(name)
                || StringUtils.isBlank(value)) {
            return new ConditionOutcome(false, "lack of attributes");
        }

        //获取environment中的值
        String property = context.getEnvironment().getProperty(prefix + "." + name);
        if (StringUtils.isBlank(property)) {
            return new ConditionOutcome(false, "null");
        }
        String[] vv = property.split(delimiter);
        List<String> values = Arrays.asList(vv).stream()
                .map(StringUtils::trim)
                .collect(Collectors.toList());
        if (values.contains(StringUtils.trim(value))) {
            return new ConditionOutcome(true, "ok");
        }
        return new ConditionOutcome(false, "not contained");
    }
}
