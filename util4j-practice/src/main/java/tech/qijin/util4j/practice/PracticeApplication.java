package tech.qijin.util4j.practice;

import com.github.pagehelper.autoconfigure.PageHelperAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication(
        scanBasePackages = {"tech.qijin.util4j"},
        exclude = PageHelperAutoConfiguration.class)
@Transactional
@MapperScan(value = {"tech.qijin.util4j.practice.dao"})
@EnableAspectJAutoProxy
public class PracticeApplication {

    public static void main(String[] args) {
        SpringApplication.run(PracticeApplication.class, args);
    }
}
