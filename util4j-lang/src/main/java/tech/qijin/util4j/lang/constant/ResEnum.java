package tech.qijin.util4j.lang.constant;

/**
 * result code
 *
 * @author michealyang
 * @date 2018/10/31
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
public enum ResEnum {
    SUCCESS(200, "success"),
    FAIL(0, "system error"),
    BAD_REQUEST(400, "bad request"),
    UNAUTHORIZED(401, "unauthorized"),
    FORBIDDEN(403, "forbidden"),
    INVALID_PARAM(422, "invalid parameters"),

    INTERNAL_ERROR(500, "internal error"),
    BAD_GATEWAY(502, "bad gateway"),

    EMPTY_USER(601, "empty user name"),
    EMPTY_PASSWORD(602, "empty password"),
    EMPTY_REPEAT_PASSWORD(603, "empty repeat password"),
    DUPLICATE_USER(604, "duplicate user name"),
    PASSWORD_MISMATCH(605, "password mismatch"),
    INVALID_EMAIL(606, "invalid email"),
    TWO_PASSWORD_INCONSISTENT(607, "Two passwords are inconsistent"),
    NICKNAME_OVERLENGTH(608, "nickname overlength"),

    INSUFFICIENT_BALANCE(701, "insufficient balance"),
    ;

    public int code;

    public String msg;

    ResEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
