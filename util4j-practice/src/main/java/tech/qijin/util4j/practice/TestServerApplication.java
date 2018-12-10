package tech.qijin.util4j.practice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication(scanBasePackages = {"tech.qijin.util4j"})
@Transactional
@MapperScan("tech.qijin.util4j.practice.dao")
public class TestServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestServerApplication.class, args);
	}
}
