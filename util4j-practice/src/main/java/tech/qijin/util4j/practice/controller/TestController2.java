package tech.qijin.util4j.practice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.Map;

/**
 * @author michealyang
 * @date 2019/3/12
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@Controller
public class TestController2 {
    @GetMapping("/hello")
    public String hello(Map<String, Object> model, String message) {
        model.put("time", new Date());
        model.put("message", "hello world");
        return "hello";
    }
}
