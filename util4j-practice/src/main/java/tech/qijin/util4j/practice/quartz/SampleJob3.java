package tech.qijin.util4j.practice.quartz;

import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import tech.qijin.util4j.practice.TestService;

/**
 * 与SampleJob4组成一种方式。这种方式写代码较少
 *
 * @author michealyang
 * @date 2018/11/22
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
public class SampleJob3 extends QuartzJobBean {

    @Autowired
    private TestService testService;

    @Override
    protected void executeInternal(JobExecutionContext context) {
        testService.sayHello("job3");
    }
}
