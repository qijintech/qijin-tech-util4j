package tech.qijin.util4j.lang.vo;

import lombok.Data;

/**
 * @author michealyang
 * @date 2018/11/13
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@Data
public class PageVo{
    public static final String PAGE_NO = "pageNo";
    public static final String PAGE_SIZE = "pageSize";
    /**
     * 当前页码
     */
    private Integer pageNo;
    /**
     * 每页展示数量
     */
    private Integer pageSize;
    /**
     * 总页数
     */
    private Integer totalPages;
}
