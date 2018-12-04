/**
 * Copyright (C) 2015 Sankuai, Inc. All Rights Reserved.
 */
package tech.qijin.util4j.web.pojo;


import lombok.Data;
import tech.qijin.util4j.lang.constant.ResEnum;
import tech.qijin.util4j.lang.dto.ResultDto;

/**
 * Restful接口的返回值对象
 */
@Data
public class ResultVo {
    private int code;
    private String message;
    private Object data;

    public static ResultVo instance(){
        return new ResultVo();
    }

    public ResultVo data(Object data) {
        this.data = data;
        return this;
    }

    public ResultVo message(String message) {
        this.message = message;
        return this;
    }

    public ResultVo code(int code){
        this.code = code;
        return this;
    }


    public ResultVo fail(int code, String message) {
        this.code = code;
        this.message = message;
        return this;
    }

    public ResultVo fail(ResEnum resEnum) {
        this.code = resEnum.code;
        this.message = resEnum.msg;
        return this;
    }

    public ResultVo fail(ResultDto resultDto) {
        this.code = resultDto.getCode();
        this.message = resultDto.getMsg();
        return this;
    }

    public ResultVo success() {
        return success(ResEnum.SUCCESS.code,ResEnum.SUCCESS.msg);
    }


    public ResultVo success(int code, String message) {
        this.code = code;
        this.message = message;
        return this;
    }

}