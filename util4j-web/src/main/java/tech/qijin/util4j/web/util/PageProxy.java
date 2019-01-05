package tech.qijin.util4j.web.util;

import com.github.pagehelper.*;
import tech.qijin.util4j.lang.vo.PageVo;

/**
 * @author michealyang
 * @date 2019/1/4
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
public class PageProxy<E> {
    private Page<E> page;

    public PageProxy(Page<E> page) {
        this.page = page;
    }

    public Page doSelectPage(ISelect select) {
        Page page = this.page.doSelectPage(select);
        PageHelperProxy.setPageVo(convertPageInfo(page));
        return page;
    }

    public PageInfo doSelectPageInfo(ISelect select) {
        PageInfo pageInfo = this.page.doSelectPageInfo(select);
        PageHelperProxy.setPageVo(convertPageInfo(pageInfo));
        return pageInfo;
    }

    private PageVo convertPageInfo(Page page) {
        PageVo pageVo = new PageVo();
        pageVo.setPageNo(page.getPageNum());
        pageVo.setPageSize(page.getPageSize());
        pageVo.setPages(page.getPages());
        pageVo.setStartRow(page.getStartRow());
        pageVo.setEndRow(page.getEndRow());
        pageVo.setTotal(page.getTotal());
        return pageVo;
    }

    private PageVo convertPageInfo(PageInfo pageInfo) {
        PageVo pageVo = new PageVo();
        pageVo.setPageNo(pageInfo.getPageNum());
        pageVo.setPageSize(pageInfo.getPageSize());
        pageVo.setPages(pageInfo.getPages());
        pageVo.setStartRow(pageInfo.getStartRow());
        pageVo.setEndRow(pageInfo.getEndRow());
        pageVo.setTotal(pageInfo.getTotal());
        return pageVo;
    }
}
