package tech.qijin.util4j.practice.controller;

import com.google.common.collect.Maps;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import tech.qijin.util4j.lang.dto.ResultDto;
import tech.qijin.util4j.practice.config.Properties;
import tech.qijin.util4j.practice.pojo.TestPageVo;
import tech.qijin.util4j.practice.pojo.TestReqVo;
import tech.qijin.util4j.practice.service.TestService;
import tech.qijin.util4j.practice.service.TestService2;
import tech.qijin.util4j.timezone.config.TimezoneProperties;
import tech.qijin.util4j.web.annotation.ResponseBodyIgnore;
import tech.qijin.util4j.web.pojo.ResultVo;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static javax.servlet.http.HttpServletResponse.SC_OK;

/**
 * @author michealyang
 * @date 2018/11/23
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@RestController
public class TestController {

    private static final Logger LOGGER = LoggerFactory.getLogger("TEST");

    @Autowired
    private TestService testService;
    @Autowired
    private TestService2 testService2;
    @Autowired
    private TestService.InnerClass innerClass;

    @Autowired
    private Properties properties;
    @Autowired
    private TimezoneProperties timezoneProperties;
    @Value("${spring.freemarker.suffix:}")
    private String fmSuffix;

    @ResponseBodyIgnore
    @GetMapping("/property")
    public Object property() {
        return ResultVo.instance().data(timezoneProperties.getValue());
    }

    @RequestMapping("/test1")
    public Object test1(TestPageVo pageVo) {
        return testService.test();
    }

    @GetMapping("/test2")
    public Object test2(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
        response.addHeader("test", "hehe");
        return new BigDecimal("-20.00");
    }

    @GetMapping("/test3")
    public Object test3() {
        return new Date();
    }

    @PostMapping("/date_post")
    public Date date(@RequestBody TestReqVo testReqVo) {
        return testReqVo.getStartTime();
    }

    @GetMapping("/date_get")
    public Date date(@RequestParam Date startTime) {
        return startTime;
    }



    @GetMapping("/swagger")
    @ApiOperation(value = "value=Map<Long,User>")
    @ApiImplicitParam(name = "map", value = "Map形式Json对象", required = true, dataType = "Map")
    public Map<Long, User> swagger() {
        return Maps.newHashMap();
    }

    @GetMapping("/swagger2")
    @ApiOperation(value = "swagger")
    public User swagger2() {
        return null;
    }

    @GetMapping("/swagger3")
    @ApiOperation(value = "swagger")
    public List<User> swagger3() {
        return null;
    }

    @GetMapping("/swagger4")
    @ApiOperation(value = "swagger")
    public ResultDto<User> swagger4() {
        return null;
    }
}
