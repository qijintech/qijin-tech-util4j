package tech.qijin.util4j.trace.pojo;

import tech.qijin.util4j.lang.constant.EnumValue;

/**
 * @author michealyang
 * @date 2019/1/6
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
public enum Channel implements EnumValue {
    TEST(0, "测试渠道"),
    MALL(100, "特产商城"),
    JOB(200, "找工作"),
    CARD(300, "名片"),
    ;

    Channel(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private int code;
    private String msg;

    @Override
    public int value() {
        return this.code;
    }

    @Override
    public String desc() {
        return this.msg;
    }
}
