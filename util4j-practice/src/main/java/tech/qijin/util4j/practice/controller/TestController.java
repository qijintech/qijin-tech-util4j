package tech.qijin.util4j.practice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.qijin.util4j.lang.vo.PageReqVo;
import tech.qijin.util4j.practice.config.ProfileConfig;
import tech.qijin.util4j.practice.config.Properties;
import tech.qijin.util4j.practice.model.User;
import tech.qijin.util4j.practice.pojo.ListBind;
import tech.qijin.util4j.practice.service.UserService;
import tech.qijin.util4j.utils.log.LogFormat;

import java.util.List;

/**
 * @author michealyang
 * @date 2018/11/23
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@RestController
public class TestController {

    private static final Logger LOGGER = LoggerFactory.getLogger("TEST");

    @Autowired
    private UserService userService;

    @Autowired
    private Properties properties;

    @GetMapping("/test1")
    public Object test1(@ModelAttribute PageReqVo pageReqVo) {
        List<User> users = userService.getUser(1);
        LOGGER.info(LogFormat.builder()
                .message("this is a test")
                .put("page", pageReqVo)
                .put("users", users)
                .build());
        return properties.getHehe();
    }

    @PostMapping("/test2")
    public Object test2(@RequestParam(value = "id") List<Integer> ids) {
        return ids;
    }
}
