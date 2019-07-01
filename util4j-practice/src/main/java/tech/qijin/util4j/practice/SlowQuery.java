package tech.qijin.util4j.practice;

import com.sun.btrace.annotations.*;

import static com.sun.btrace.BTraceUtils.Strings.strcat;
import static com.sun.btrace.BTraceUtils.println;
import static com.sun.btrace.BTraceUtils.str;

/**
 * @author michealyang
 * @date 2019/4/29
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@BTrace
public class SlowQuery {
    @OnMethod(clazz = "com.aviagames.game.service.impl", method = "/.*/", location = @Location(Kind.RETURN))
    public static void slowQuery1(@ProbeClassName String pcn, @ProbeMethodName String probeMethod, @Duration long duration) {
        println(strcat("类：", pcn));
        println(strcat("方法：", probeMethod));
        println(strcat("时长：", str(duration / 1000000)));
    }

    @OnMethod(clazz = "com.aviagames.game.service.impl.GameServiceImpl",
            method = "pageGameBo",
            location = @Location(Kind.RETURN))
    public static void slowQuery2(@ProbeClassName String pcn, @ProbeMethodName String probeMethod, @Duration long duration) {
        println(strcat("类：", pcn));
        println(strcat("方法：", probeMethod));
        println(strcat("时长：", str(duration / 1000000)));
    }
}
