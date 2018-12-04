package tech.qijin.util4j.schedule.quartz;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.quartz.Job;

/**
 * @author michealyang
 * @date 2018/11/26
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@Data
@AllArgsConstructor
public class QuartzJob {
    private Class<? extends Job> clazz;
    private String expression;
    private String group;
}
