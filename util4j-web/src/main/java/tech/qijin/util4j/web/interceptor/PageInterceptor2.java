package tech.qijin.util4j.web.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageHelper;

import lombok.extern.slf4j.Slf4j;
import tech.qijin.util4j.lang.vo.PageVo;
import tech.qijin.util4j.utils.LogFormat;
import tech.qijin.util4j.web.filter.RequestWrapper;
import tech.qijin.util4j.web.util.PageHelperProxy;
import tech.qijin.util4j.web.util.ServletUtil;

/**
 * 这个用id进行分页，offset永远为0
 *
 * @author michealyang
 * @date 2018/11/23
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@Slf4j
public class PageInterceptor2 implements HandlerInterceptor {
    private static final Integer DEFAULT_PAGE_SIZE = 10;
    private static final Integer PAGE_SIZE_LIMIT = 500;
    private static final Long MAX_ID = Long.MAX_VALUE;
    private static final Long MIN_ID = -1L;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(request instanceof RequestWrapper)) {
            return true;
        }
        RequestWrapper requestWrapper = (RequestWrapper) request;
        Map<String, String[]> params = ServletUtil.getParameters(requestWrapper);
        String[] pageSizeArr = params.get(PageVo.PAGE_SIZE);
        String[] minIdArr = params.get(PageVo.MIN_ID);
        String[] maxIdArr = params.get(PageVo.MAX_ID);
        int pageSize = DEFAULT_PAGE_SIZE;
        if (pageSizeArr != null && pageSizeArr.length > 0) {
            pageSize = Integer.valueOf(pageSizeArr[0]);
        }
        if (pageSize > PAGE_SIZE_LIMIT) {
            log.error(LogFormat.builder().message("too large page size").build());
            return false;
        }
        Long maxId = MAX_ID;
        Long minId = MIN_ID;
        PageHelper.startPage(0, pageSize, false);

        if (maxIdArr != null && maxIdArr.length > 0) {
            maxId = Long.valueOf(maxIdArr[0]);
        } else if (minIdArr != null) {
            minId = Long.valueOf(minIdArr[0]);
        } else {
            //maxId和minId都不设置时，默认降序排序
        }
        PageHelperProxy.setReqPageVo(new PageVo(maxId, minId));
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        PageHelperProxy.clear();
    }
}
