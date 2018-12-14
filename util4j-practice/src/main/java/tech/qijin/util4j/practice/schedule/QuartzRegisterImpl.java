package tech.qijin.util4j.practice.schedule;

import org.springframework.stereotype.Component;
import tech.qijin.util4j.schedule.quartz.AbstractQuartzJobRegister;
import tech.qijin.util4j.schedule.quartz.QuartzJob;

import java.util.List;

/**
 * @author michealyang
 * @date 2018/12/12
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@Component
public class QuartzRegisterImpl extends AbstractQuartzJobRegister {
    @Override
    public void registerJobs(List<QuartzJob> jobs) {
        jobs.add(new QuartzJob(Job1.class, "0/20 * * * * ?", null));
    }
}
