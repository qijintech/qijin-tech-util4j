package tech.qijin.util4j.practice.service;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;
import tech.qijin.util4j.aop.annotation.ListCheck;
import tech.qijin.util4j.lang.constant.ResEnum;
import tech.qijin.util4j.lang.dto.ResultDto;
import tech.qijin.util4j.practice.pojo.ListBind;
import tech.qijin.util4j.utils.MAssert;
import tech.qijin.util4j.utils.ResBuilder;

import java.util.List;

/**
 * @author michealyang
 * @date 2019/1/8
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@Service
public class TestService {
    public String test() {
        return "test";
    }

    @Service
    public class InnerClass{
        public String hehe() {
            return "hehe";
        }
    }
}
