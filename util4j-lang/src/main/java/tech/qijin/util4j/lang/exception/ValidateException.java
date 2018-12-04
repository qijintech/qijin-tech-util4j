package tech.qijin.util4j.lang.exception;


import tech.qijin.util4j.lang.constant.ResEnum;

public class ValidateException extends RuntimeException implements BaseException {

    private static final long serialVersionUID = -2536411654971150859L;
    private static final int DEFAULT_CDOE = ResEnum.BAD_GATEWAY.code;
    private final transient int code;
    private final transient Object data;



    public ValidateException(int code, String message){
        this(code,null,message,null);
    }


    public ValidateException(int code, Object data, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.data = data;
    }


    @Override
    public int getCode() {
        return code;
    }

    @Override
    public Object getData() {
        return data;
    }

    @Override
    public String toString() {
        return "ValidateException{" + "code=" + code + ", data=" + data + '}';
    }
}
