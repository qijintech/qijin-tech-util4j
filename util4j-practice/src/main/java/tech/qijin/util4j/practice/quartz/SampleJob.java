package tech.qijin.util4j.practice.quartz;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import tech.qijin.util4j.practice.TestService;
import tech.qijin.util4j.schedule.quartz.QuartzBaseBean;

import java.text.ParseException;

/**
 * 与SampleJob2组成一种方式。这种方式写代码较多
 *
 * @author michealyang
 * @date 2018/11/22
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@Configuration
public class SampleJob extends QuartzBaseBean {

    private static final String UNIQUE_ID = "SampleJob";
    private static final String JOB_DETAIL = UNIQUE_ID + "Detail";
    private static final String JOB_TRIGGER = UNIQUE_ID + "Trigger";

    @Autowired
    private TestService testService;

    @Override
    protected void doExecute() {
//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        testService.sayHello("job");
    }

    @Override
    protected String getCronExpression() {
        return "0/1 * * * * ?";
    }

    @Override
    protected Class<? extends Job> definedClass() {
        return SampleJob.class;
    }

    @Bean(name = JOB_DETAIL)
    public JobDetailFactoryBean job() {
        return jobBase(SampleJob.class);
    }

    @Bean(name = JOB_TRIGGER)
    public CronTriggerFactoryBean trigger(@Qualifier(JOB_DETAIL) JobDetail job) throws ParseException {
        return triggerBase(job);
    }
}
