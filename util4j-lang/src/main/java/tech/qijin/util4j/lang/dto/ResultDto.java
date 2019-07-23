package tech.qijin.util4j.lang.dto;

import java.io.Serializable;

import tech.qijin.util4j.lang.constant.ResEnum;

/**
 * @author michealyang
 * @param <T>
 */
public class ResultDto<T> implements Serializable {
    private int code;
    private String msg;
    private T data;

    public int getCode() {
        return code;
    }

    public ResultDto setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public ResultDto setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public T getData() {
        return data;
    }

    public ResultDto setData(T data) {
        this.data = data;
        return this;
    }


    public Boolean isSuccess() {
        return ResEnum.SUCCESS.code == code;
    }
}
