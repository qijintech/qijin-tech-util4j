package tech.qijin.util4j.practice.pojo;

import tech.qijin.util4j.lang.constant.EnumValue;

/**
 * @author michealyang
 * @date 2018/12/10
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
public enum ColorEnm implements EnumValue {
    BLUE(1, "blue"),
    GREEN(2, "green");

    ColorEnm(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    private int value;
    private String desc;

    @Override
    public int value() {
        return 0;
    }

    @Override
    public String desc() {
        return null;
    }
}
