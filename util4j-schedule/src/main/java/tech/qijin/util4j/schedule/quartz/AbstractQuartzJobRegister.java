package tech.qijin.util4j.schedule.quartz;

import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import tech.qijin.util4j.schedule.IScheduler;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.util.List;

/**
 * @author michealyang
 * @date 2018/11/26
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
public abstract class AbstractQuartzJobRegister implements IScheduler {

    protected static final Logger LOGGER = LoggerFactory.getLogger("Quartz");

    private static final String DEFAULT_GROUP = "group1";

    @Autowired
    private Scheduler scheduler;

    protected List<QuartzJob> jobs = Lists.newArrayList();

    @PostConstruct
    public void init() throws ParseException, SchedulerException {
        registerJobs(jobs);
        if (CollectionUtils.isEmpty(jobs)) {
            LOGGER.warn("no quartz job");
            return;
        }
        for (QuartzJob job : jobs) {
            register(job);
        }
    }

    public abstract void registerJobs(List<QuartzJob> jobs);

    private void register(QuartzJob job) throws ParseException, SchedulerException {
        JobKey jobKey = getJobKey(job.getClazz(), getGroup(job.getGroup()));
        TriggerKey triggerKey = getTriggerKey(job.getClazz(), getGroup(job.getGroup()));
        CronTrigger storedTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        JobDetail storedJobDetail = scheduler.getJobDetail(getJobKey(job.getClazz(), getGroup(job.getGroup())));
        if (storedTrigger == null && storedJobDetail == null) {
            //trigger和job之前不存在，则注册trigger和job
            LOGGER.info("add new trigger and job. triggerKey={}, jobKey={}",
                    triggerKey.getName(), jobKey.getName());
            JobDetail jobDetail = registerJob(job.getClazz(), getGroup(job.getGroup()));
            CronTrigger trigger = registerTrigger(jobDetail,
                    triggerKey,
                    getGroup(job.getGroup()),
                    job.getExpression());
            scheduler.scheduleJob(jobDetail, trigger);
        } else if (storedJobDetail == null) {
            //如果只是job不存在，则创建job
            LOGGER.info("only job not exist. triggerKey={}, jobKey={}",
                    triggerKey.getName(), jobKey.getName());
            rescheduleJob(storedTrigger, job);
        } else if (storedTrigger == null) {
            LOGGER.info("only trigger not exist. triggerKey={}, jobKey={}",
                    triggerKey.getName(), jobKey.getName());
            rescheduleTrigger(job, jobKey, triggerKey);
        } else {
            LOGGER.info("trigger and job already exist. triggerKey={}, jobKey={}",
                    triggerKey.getName(), jobKey.getName());
            //如果trigger的expression被修改了，则更新trigger
            if (isTriggerChanged(storedTrigger, job)) {
                LOGGER.info("trigger exist, but should updated. triggerKey={}, jobKey={}",
                        triggerKey.getName(), jobKey.getName());
                updateTrigger(storedTrigger, job);
            }
        }
    }

    private void rescheduleJob(CronTrigger trigger, QuartzJob job) throws ParseException, SchedulerException {
        JobDetail jobDetail = registerJob(job.getClazz(), getGroup(job.getGroup()));
        CronTrigger newTrigger = registerTrigger(jobDetail, trigger.getKey(), getGroup(job.getGroup()), trigger.getCronExpression());
        scheduler.rescheduleJob(trigger.getKey(), newTrigger);
    }

    private void rescheduleTrigger(QuartzJob job, JobKey jobKey, TriggerKey triggerKey) throws SchedulerException {
        CronTrigger newTrigger = TriggerBuilder.newTrigger()
                .forJob(jobKey)
                .withIdentity(triggerKey.getName(), getGroup(job.getGroup()))
                .withSchedule(CronScheduleBuilder.cronSchedule(job.getExpression()))
                .build();
        scheduler.scheduleJob(newTrigger);
    }

    private void updateTrigger(CronTrigger oldTrigger, QuartzJob job) throws SchedulerException {
        // obtain a builder that would produce the trigger
        CronTrigger trigger = TriggerBuilder.newTrigger()
                .withSchedule(CronScheduleBuilder.cronSchedule(job.getExpression()))
                .withIdentity(oldTrigger.getKey().getName(), getGroup(job.getGroup()))
                .build();
        scheduler.rescheduleJob(oldTrigger.getKey(), trigger);
    }

    private JobDetail registerJob(Class<? extends Job> clazz, String group) {
        return JobBuilder.newJob(clazz)
                .withIdentity(getJobKey(clazz, group))
                .withDescription("Invoke Sample Job service...")
                .storeDurably()
                .build();
    }

    private CronTrigger registerTrigger(JobDetail job,
                                        TriggerKey triggerKey,
                                        String group,
                                        String expression) throws ParseException {
        return TriggerBuilder.newTrigger()
                .forJob(job)
                .withIdentity(triggerKey.getName(), getGroup(group))
                .withSchedule(CronScheduleBuilder.cronSchedule(expression))
                .build();
    }

    private JobKey getJobKey(Class<? extends Job> clazz, String group) {
        return JobKey.jobKey(clazz.getSimpleName() + "Detail", group);
    }

    private TriggerKey getTriggerKey(Class<? extends Job> clazz, String group) {
        return TriggerKey.triggerKey(clazz.getSimpleName() + "Trigger", group);
    }

    private String getGroup(String group) {
        return group == null ? DEFAULT_GROUP : group;
    }

    private boolean isTriggerChanged(CronTrigger trigger, QuartzJob job) {
        return !job.getExpression().equals(trigger.getCronExpression());
    }
}
