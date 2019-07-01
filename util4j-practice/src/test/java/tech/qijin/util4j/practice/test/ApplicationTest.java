package tech.qijin.util4j.practice.test;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author UnitTest
 */

@SpringBootApplication(scanBasePackages={"tech.qijin.util4j"})
@MapperScan("tech.qijin.util4j")
@SpringBootTest
public class ApplicationTest {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationTest.class, args);
    }
}
