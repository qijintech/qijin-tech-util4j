package tech.qijin.util4j.practice.quartz;

import org.springframework.stereotype.Component;
import tech.qijin.util4j.schedule.quartz.AbstractQuartzJobRegister;
import tech.qijin.util4j.schedule.quartz.QuartzJob;

import java.util.List;

/**
 * @author michealyang
 * @date 2018/11/26
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@Component
public class QuartzRegisterImpl extends AbstractQuartzJobRegister {
    @Override
    public void registerJobs(List<QuartzJob> jobs) {
        jobs.add(new QuartzJob(SampleJob3.class, "0/3 * * * * ?", null));
        jobs.add(new QuartzJob(SampleJob4.class, "0/4 * * * * ?", null));
    }
}
