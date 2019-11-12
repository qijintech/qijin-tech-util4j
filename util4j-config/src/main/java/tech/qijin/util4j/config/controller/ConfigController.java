package tech.qijin.util4j.config.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import tech.qijin.util4j.config.AutoConfig;
import tech.qijin.util4j.config.Config;
import tech.qijin.util4j.web.handler.annotation.ResponseBodyIgnore;

/**
 * @author michealyang
 * @date 2019/4/9
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@Slf4j
@RestController
@RequestMapping("/config")
public class ConfigController {
    @Autowired
    private AutoConfig autoConfig;
    @Autowired
    private Config config;


    @ResponseBodyIgnore
    @GetMapping("/reload")
    public String reload() {
        autoConfig.loadProperties();
        return "OK";
    }

    @ResponseBodyIgnore
    @GetMapping("/get")
    public Object get(String key) {
        return config.get(key, null);
    }
}
