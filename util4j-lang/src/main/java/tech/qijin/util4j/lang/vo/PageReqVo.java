package tech.qijin.util4j.lang.vo;

import lombok.Data;
import lombok.ToString;

/**
 * @author michealyang
 * @date 2018/11/13
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@Data
@ToString
public class PageReqVo {
    /**
     * 当前页码
     */
    private Integer pageNo;
    /**
     * 每页展示数量
     */
    private Integer pageSize;
}
