package tech.qijin.util4j.trace.pojo;

import tech.qijin.util4j.lang.constant.EnumValue;

/**
 * @author michealyang
 * @date 2019/1/6
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
public enum Channel implements EnumValue<String> {
    TEST("测试渠道"),
    MALL("特产商城"),
    JOB("找工作"),
    CARD("名片"),
    ;

    Channel(String msg) {
        this.msg = msg;
    }

    private int code;
    private String msg;

    @Override
    public String value() {
        return this.name();
    }

    @Override
    public String desc() {
        return this.msg;
    }
}
