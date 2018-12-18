package tech.qijin.util4j.utils;


import tech.qijin.util4j.lang.constant.ResEnum;
import tech.qijin.util4j.lang.dto.ResultDto;

public class ResBuilder {

    /**
     * 校验结果是否成功
     * @param resultDto
     * @return
     */
    public static boolean isSuccess(ResultDto resultDto) {
        if (resultDto == null) {
            return false;
        }
        return resultDto.getCode() == ResEnum.SUCCESS.code;
    }

    public static boolean isNotSuccess(ResultDto resultDto) {
        if (resultDto == null) {
            return true;
        }
        return resultDto.getCode() != ResEnum.SUCCESS.code;
    }

    public static ResultDto genSuccess() {
        return new ResultDto()
                .setCode(ResEnum.SUCCESS.code)
                .setMsg(ResEnum.SUCCESS.msg);
    }

    public static ResultDto genSuccess(String msg) {
        return new ResultDto()
                .setCode(ResEnum.SUCCESS.code)
                .setMsg(msg);
    }

    public static ResultDto genData(Object data) {
        return new ResultDto()
                .setCode(ResEnum.SUCCESS.code)
                .setMsg(ResEnum.SUCCESS.msg)
                .setData(data);
    }

    public static ResultDto genSuccess(String msg, Object data) {
        return new ResultDto()
                .setCode(ResEnum.SUCCESS.code)
                .setMsg(msg)
                .setData(data);
    }

    public static ResultDto genError(ResultDto resultDto) {
        if (resultDto == null) {
            return new ResultDto()
                    .setCode(ResEnum.FAIL.code)
                    .setMsg(ResEnum.FAIL.msg);
        }
        return new ResultDto()
                .setCode(resultDto.getCode())
                .setMsg(resultDto.getMsg());
    }

    public static ResultDto genError(ResEnum resEnum) {
        return new ResultDto()
                .setCode(resEnum.code)
                .setMsg(resEnum.msg);
    }

    public static ResultDto genError(int code, String msg) {
        return new ResultDto()
                .setCode(code)
                .setMsg(msg);
    }

    public static ResultDto genError(int code, String msg, Object data) {
        return new ResultDto()
                .setCode(code)
                .setMsg(msg)
                .setData(data);
    }
}
