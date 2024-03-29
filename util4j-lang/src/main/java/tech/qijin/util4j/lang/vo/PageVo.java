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
    private static final Integer DEFAULT_PAGE_SIZE = 10;

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

    /**
     * 是否是首页
     *
     * @return
     */
    public boolean isFirstPage() {
        if (pageNo == null) return true;
        if (pageNo <= 1) return true;
        return false;
    }

    public String orderBy(String column) {
        return orderBy(column, "desc");
    }

    public String orderBy(String column, String direction) {
        return String.format("%s %s limit %d, %d", column, direction, (pageNo - 1) * pageSize, pageSize);
    }

    public static PageVo check(PageVo pageVo, Integer defaultPageSize) {
        Integer pageSize = defaultPageSize != null ? defaultPageSize : DEFAULT_PAGE_SIZE;
        if (pageVo == null) {
            return new PageVo(1, pageSize);
        }
        if (pageVo.getPageNo() == null || pageVo.getPageNo() < 1) {
            pageVo.setPageNo(1);
        }
        if (pageVo.getPageSize() == null || pageVo.getPageSize() <= 0) {
            pageVo.setPageSize(pageSize);
        }
        return pageVo;
    }

}
