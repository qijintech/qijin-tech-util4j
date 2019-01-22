package tech.qijin.util4j.cache.test;

import lombok.Data;

import java.io.Serializable;

/**
 * @author michealyang
 * @date 2019/1/21
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@Data
public class AClass implements Serializable {
    private static final long serialVersionUID = -3912392628108653521L;
    private String a;
    private Integer b;
    private Boolean c;
    private BClass bClass;
}
