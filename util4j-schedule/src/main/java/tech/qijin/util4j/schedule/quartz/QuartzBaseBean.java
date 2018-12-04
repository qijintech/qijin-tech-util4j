package tech.qijin.util4j.schedule.quartz;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @author michealyang
 * @date 2018/11/22
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
public abstract class QuartzBaseBean extends QuartzJobBean {
    protected static final Logger LOGGER = LoggerFactory.getLogger("Quartz");

    protected abstract void doExecute();

    protected abstract String getCronExpression();

    protected abstract Class<? extends Job> definedClass();

    @Override
    protected void executeInternal(JobExecutionContext context) {
        doExecute();
    }

    public JobDetailFactoryBean jobBase(Class<? extends Job> clazz) {
        JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
        jobDetailFactory.setJobClass(clazz);
        jobDetailFactory.setDurability(true);
        jobDetailFactory.setName(clazz.getSimpleName());
        jobDetailFactory.setDescription("Invoke Sample Job service...");
        jobDetailFactory.setDurability(true);
        return jobDetailFactory;
    }

    public CronTriggerFactoryBean triggerBase(JobDetail job) {
        CronTriggerFactoryBean cronTrigger = new CronTriggerFactoryBean();
        cronTrigger.setCronExpression(getCronExpression());
        cronTrigger.setJobDetail(job);
        return cronTrigger;
    }
}
