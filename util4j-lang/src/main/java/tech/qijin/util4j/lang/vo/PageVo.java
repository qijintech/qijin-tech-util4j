package tech.qijin.util4j.lang.vo;

import lombok.Data;

/**
 * @author michealyang
 * @date 2018/11/13
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@Data
public class PageVo {
    public static final String PAGE_NO = "pageNo";
    public static final String PAGE_SIZE = "pageSize";
    public static final String MIN_ID = "minId";
    public static final String MAX_ID = "maxId";

    public PageVo() {
    }

    public PageVo(Integer pageNo, Integer pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public PageVo(Long maxId, Long minId) {
        this.maxId = maxId;
        this.minId = minId;
    }

    /**
     * 当前页码
     */
    private Integer pageNo;
    /**
     * 每页展示数量
     */
    private Integer pageSize;
    /**
     * 总数
     */
    private long total;
    /**
     * 总页数
     */
    private int pages;
    /**
     * 查询的结果需要<=maxId
     */
    private Long maxId;
    /**
     * 查询的结果需要>=minId
     */
    private Long minId;

}
