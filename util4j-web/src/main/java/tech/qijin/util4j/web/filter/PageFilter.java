package tech.qijin.util4j.web.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.github.pagehelper.PageHelper;

import lombok.extern.slf4j.Slf4j;
import tech.qijin.util4j.lang.vo.PageVo;
import tech.qijin.util4j.utils.LogFormat;
import tech.qijin.util4j.web.util.ServletUtil;

/**
 * 需要结合PageHelper使用
 *
 * @author michealyang
 * @date 2018/11/23
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@Slf4j
@Deprecated
public class PageFilter implements Filter {
    private static final Integer DEFAULT_PAGE_SIZE = 10;
    private static final Integer PAGE_SIZE_LIMIT = 500;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        RequestWrapper requestWrapper = (RequestWrapper) request;
        Map<String, String[]> params = ServletUtil.getParameters(requestWrapper);
        String[] pageNoArr = params.get(PageVo.PAGE_NO);
        String[] pageSizeArr = params.get(PageVo.PAGE_SIZE);
        int pageNo = 0;
        int pageSize = DEFAULT_PAGE_SIZE;
        if (pageNoArr != null && pageNoArr.length > 0) {
            pageNo = Integer.valueOf(pageNoArr[0]);
        }
        if (pageSizeArr != null && pageSizeArr.length > 0) {
            pageSize = Integer.valueOf(pageSizeArr[0]);
        }
        if (pageSize > PAGE_SIZE_LIMIT) {
            log.error(LogFormat.builder().message("too large page size").build());
            return;
        }
        PageHelper.startPage(pageNo, pageSize);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
