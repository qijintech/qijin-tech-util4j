package tech.qijin.util4j.web.filter;

import lombok.extern.slf4j.Slf4j;
import tech.qijin.util4j.trace.pojo.Channel;
import tech.qijin.util4j.trace.util.ChannelUtil;
import tech.qijin.util4j.utils.LogFormat;
import tech.qijin.util4j.web.util.ServletUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

/**
 * @author michealyang
 * @date 2018/11/23
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@Slf4j
public class ChannelFilter implements Filter {

    private static final String CHANNEL_KEYWORD = "channel";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        Optional<String> channelOpt = ServletUtil.getHeader((HttpServletRequest) request, CHANNEL_KEYWORD);
        if (channelOpt.isPresent()) {
            Channel channel = Channel.valueOf(channelOpt.get());
            if (channel == null) {
                log.error(LogFormat.builder().message("invalid channel")
                        .put("channel", channelOpt.get()).build());
                //TODO 返回错误信息
                return;
            }
            ChannelUtil.setChannel(channel);
            chain.doFilter(request, response);
        } else {
            log.error(LogFormat.builder().message("empty channel").build());
            //TODO 返回错误信息
        }
    }

    @Override
    public void destroy() {

    }
}
