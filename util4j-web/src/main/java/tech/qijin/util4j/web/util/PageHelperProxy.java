package tech.qijin.util4j.web.util;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import tech.qijin.util4j.lang.vo.PageVo;

/**
 * 本类的作用主要是为了在返回结果中返回分页信息，如总页数，总条目数量等
 * <p>使用规范:
 * <code>
 * PageHelperProxy.getLocalPage().doSelect(
 * ()->xxxDao.selectByExample(example)
 * );
 * </code>
 *
 * </p>
 * <p>App分页由于是下拉操作模式，通常不需要返回总页数。这种情况下可不使用本类，直接使用{@link PageHelper}即可
 * </p>
 *
 * @author michealyang
 * @date 2019/1/4
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
public class PageHelperProxy {
    /**
     * 缓存请求参数中的pageNo和pageSize
     */
    private static final ThreadLocal<PageVo> REQ_PAGE_VO = new ThreadLocal<PageVo>();
    /**
     * 缓存响应结果中的pageNo和pageSize
     */
    private static final ThreadLocal<PageVo> RES_PAGE_VO = new ThreadLocal<PageVo>();

    public static <T> PageProxy<T> getLocalPage() {
        Page page = PageHelper.getLocalPage();
        if (page == null && getReqPageVo() != null) {
            //说明执行过其他的sql，导致page被清除，需要重置一下
            PageVo pageVo = getReqPageVo();
            PageHelper.startPage(pageVo.getPageNo() - 1, pageVo.getPageSize());
        }
        return new PageProxy<T>(page);
    }

    public static void setResPageVo(PageVo pageVo) {
        if (RES_PAGE_VO.get() != null) {
            throw new UnsupportedOperationException("more than once page operation in one thread is not supported");
        }
        RES_PAGE_VO.set(pageVo);
    }

    public static PageVo getResPageVo() {
        return RES_PAGE_VO.get();
    }

    public static void setReqPageVo(PageVo pageVo) {
        REQ_PAGE_VO.set(pageVo);
    }

    public static PageVo getReqPageVo() {
        return REQ_PAGE_VO.get();
    }

    public static Long getMinId() {
        return REQ_PAGE_VO.get().getMinId();
    }

    public static Long getMaxId() {
        return REQ_PAGE_VO.get().getMaxId();
    }

    public static void clear() {
        RES_PAGE_VO.remove();
        REQ_PAGE_VO.remove();
    }
}
