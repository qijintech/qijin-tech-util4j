package tech.qijin.util4j.web.interceptor;

import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import tech.qijin.util4j.lang.vo.PageVo;
import tech.qijin.util4j.trace.pojo.EnvEnum;
import tech.qijin.util4j.trace.util.EnvUtil;
import tech.qijin.util4j.utils.LogFormat;
import tech.qijin.util4j.web.filter.RequestWrapper;
import tech.qijin.util4j.web.util.PageHelperProxy;
import tech.qijin.util4j.web.util.ServletUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 这个用offset进行分页，性能不太好
 *
 * @author michealyang
 * @date 2018/11/23
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@Slf4j
public class PageInterceptor implements HandlerInterceptor {
    private static final Integer DEFAULT_PAGE_SIZE = 10;
    private static final Integer PAGE_SIZE_LIMIT = 500;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(request instanceof RequestWrapper)) {
            return true;
        }
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
            return false;
        }
        PageHelperProxy.setReqPageVo(new PageVo(pageNo, pageSize));
        PageHelper.startPage(pageNo - 1, pageSize);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        PageHelper.clearPage();
        PageHelperProxy.clear();
    }
}
