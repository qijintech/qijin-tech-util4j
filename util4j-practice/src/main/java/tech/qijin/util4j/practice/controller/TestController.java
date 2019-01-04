package tech.qijin.util4j.practice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.qijin.util4j.lang.constant.ResEnum;
import tech.qijin.util4j.practice.config.Properties;
import tech.qijin.util4j.practice.model.User;
import tech.qijin.util4j.practice.pojo.TestPageVo;
import tech.qijin.util4j.practice.service.UserService;
import tech.qijin.util4j.utils.LogFormat;
import tech.qijin.util4j.utils.MAssert;
import tech.qijin.util4j.web.pojo.ResultVo;

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

    @RequestMapping("/test1")
    public Object test1(TestPageVo pageVo) {
        List<User> users = userService.getUser(1);
        LOGGER.info(LogFormat.builder()
                .message("this is a test")
                .put("page", pageVo)
                .put("users", users)
                .build());
        return ResultVo.instance().code(ResEnum.BAD_REQUEST.code);
    }

    @PostMapping("/test2")
    public Object test2(@RequestParam(value = "id") List<Integer> ids) {
        return ids;
    }
}
