package tech.qijin.util4j.web.filter;

import com.github.pagehelper.PageHelper;
import tech.qijin.util4j.lang.vo.PageVo;
import tech.qijin.util4j.web.util.ServletUtil;

import javax.servlet.*;
import java.io.IOException;
import java.util.Map;

/**
 * 需要结合PageHelper使用
 *
 * @author michealyang
 * @date 2018/11/23
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
public class PageFilter implements Filter {

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
        int pageSize = Integer.MAX_VALUE;
        if (pageNoArr != null && pageNoArr.length > 0) {
            pageNo = Integer.valueOf(pageNoArr[0]);
        }
        if (pageSizeArr != null && pageSizeArr.length > 0) {
            pageSize = Integer.valueOf(pageSizeArr[0]);
        }
        PageHelper.startPage(pageNo, pageSize);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
