package tech.qijin.util4j.web.util;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import tech.qijin.util4j.lang.vo.PageVo;

/**
 * @author michealyang
 * @date 2019/1/4
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
public class PageHelperProxy {
    private static final ThreadLocal<PageVo> LOCAL_PAGE_VO = new ThreadLocal<PageVo>();
    private static final ThreadLocal<Long> MIN_ID = new ThreadLocal<Long>();
    private static final ThreadLocal<Long> MAX_ID = new ThreadLocal<Long>();

    public static <T> PageProxy<T> getLocalPage() {
        Page page = PageHelper.getLocalPage();
        return new PageProxy<T>(page);
    }

    public static void setPageVo(PageVo pageInfo) {
        if (LOCAL_PAGE_VO.get() != null) {
            throw new IllegalStateException("more than once page operation in one thread is not supported");
        }
        LOCAL_PAGE_VO.set(pageInfo);
    }

    public static PageVo getPageVo() {
        return LOCAL_PAGE_VO.get();
    }

    public static void clear() {
        LOCAL_PAGE_VO.remove();
    }

    public static Long getMinId() {
        return MIN_ID.get();
    }

    public static Long getMaxId() {
        return MAX_ID.get();
    }

    public static void setMinId(Long minId) {
        MIN_ID.set(minId);
    }

    public static void setMaxId(Long maxId) {
        MAX_ID.set(maxId);
    }
}
