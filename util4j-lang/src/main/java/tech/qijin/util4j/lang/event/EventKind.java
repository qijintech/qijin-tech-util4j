package tech.qijin.util4j.lang.event;

import tech.qijin.util4j.lang.constant.EnumValue;

/**
 * 事件类型
 */
public enum EventKind implements EnumValue<String> {
    REGISTER("注册事件"),
    LOGIN("登录事件"),
    SHARE("分享事件"),
    INVITE("成功邀请事件"),
    OPEN("打开事件"),

    // 业务相关
    FEED_PUBLISH("发布Feed事件"),

    ;

    EventKind(String description) {
        this.description = description;
    }

    private String description;

    @Override
    public String value() {
        return this.name();
    }

    @Override
    public String desc() {
        return this.description;
    }
}
