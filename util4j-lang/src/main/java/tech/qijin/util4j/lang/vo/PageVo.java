package tech.qijin.util4j.lang.vo;

import lombok.Data;

/**
 * @author michealyang
 * @date 2018/11/13
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@Data
public class PageResVo extends PageReqVo {
    /**
     * 总页数
     */
    private Integer totalPages;
}
