# schedule - 定时任务模块

目前集成了quartz

## 使用方法

Step1: 引入依赖

```xml
<dependency>
    <groupId>tech.qijin.util4j</groupId>
    <artifactId>schedule</artifactId>
</dependency>

```

Step2: 转接执行定时任务的类

```java
//必须继承自类QuartzJobBean
public class Job1 extends QuartzJobBean {
	@Autowired
	private TestService testService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        System.out.println("job1");
    }
}
```

Step3: 注册Step2中写的类

如果没有继承`AbstractQuartzJobRegister`类，则实现一个。如果已经实现了，则在里面添加即可。

这里假设没有继承`AbstractQuartzJobRegister`，则需要继承一个:

```java
@Component
public class QuartzRegisterImpl extends AbstractQuartzJobRegister {
    @Override
    public void registerJobs(List<QuartzJob> jobs) {
        jobs.add(new QuartzJob(Job1.class, "0/3 * * * * ?", null));
        jobs.add(new QuartzJob(Job2.class, "0/4 * * * * ?", null));
    }
}
```

在`registerJobs()`方法中将Step2中写的类，add进去即可。

* 第一个参数: 定时任务类class

* 第二个参数: 执行规则。类似于crontab

* 第三个参数: group名，为null的话，默认为"group1"

Step4: 添加配置

这个配置添加一次即可

在application.properties中，添加如下一行：

```
spring.quartz.schedulerName=usercenter
```

**注意**
* 当有多个不同工程共享quartz的时候，这个配置非常重要。需要用这个来区分不同工程，并且需要保证唯一性
* 使用这个配置的spring boot 版本不能太低，否则不支持。建议spring boot 2.1.1及一行版本
* 建议使用本工程module名，用以和其他工程区分

注:

使用dubbo时，由于dubbo的bug，在`DubboConfigBindingBeanPostProcessor`类中，会出现空指针报错。

解决办法：

1. 升级高版本

2. 修改源码，并上传包到私有仓库
