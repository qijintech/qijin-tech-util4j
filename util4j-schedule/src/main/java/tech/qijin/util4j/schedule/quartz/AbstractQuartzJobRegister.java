package tech.qijin.util4j.schedule.quartz;

import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.util.List;

/**
 * @author michealyang
 * @date 2018/11/26
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
public abstract class AbstractQuartzJobRegister {

    protected static final Logger LOGGER = LoggerFactory.getLogger("Quartz");

    private static final String DEFAULT_GROUP = "group1";

    @Autowired
    private Scheduler scheduler;

    protected List<QuartzJob> jobs = Lists.newArrayList();

    @PostConstruct
    public void init() throws ParseException, SchedulerException {
        registerJobs(jobs);
        LOGGER.info("==========");
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
        JobDetail jobDetail = registerJob(job.getClazz());
        String triggerKey = getTriggerKey(job.getClazz());
        CronTrigger trigger = registerTrigger(jobDetail,
                triggerKey,
                job.getGroup(),
                job.getExpression());
        if (scheduler.getJobDetail(JobKey.jobKey(getJobKey(job.getClazz()))) == null) {
            LOGGER.info("add new job detail. job key={}", getJobKey(job.getClazz()));
            scheduler.scheduleJob(jobDetail, trigger);
        }
        LOGGER.info("exist job detail. job key={}", getJobKey(job.getClazz()));
    }

    private JobDetail registerJob(Class<? extends Job> clazz) {
        return JobBuilder.newJob(clazz)
                .withIdentity(getJobKey(clazz))
                .withDescription("Invoke Sample Job service...")
                .storeDurably()
                .build();
    }

    private CronTrigger registerTrigger(JobDetail job, String triggerKey, String group, String expression) throws ParseException {
        return TriggerBuilder.newTrigger()
                .forJob(job)
                .withIdentity(triggerKey, getGroup(group))
                .withSchedule(CronScheduleBuilder.cronSchedule(expression))
                .build();
    }

    private String getJobKey(Class<? extends Job> clazz) {
        return clazz.getSimpleName() + "Job";
    }

    private String getTriggerKey(Class<? extends Job> clazz) {
        return clazz.getSimpleName() + "Trigger";
    }

    private String getGroup(String group) {
        return group == null ? DEFAULT_GROUP : group;
    }
}
