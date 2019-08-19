package tech.qijin.util4j.websocket.server.handler;

import tech.qijin.util4j.trace.util.EnvUtil;
import tech.qijin.util4j.trace.util.TraceUtil;
import tech.qijin.util4j.utils.LogFormat;
import tech.qijin.util4j.websocket.server.WebSocketUserHolder;
import tech.qijin.util4j.websocket.spi.WebSocketProvider;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author michealyang
 * @date 2019/1/25
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@ChannelHandler.Sharable
@Component
@Slf4j
public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    @Autowired
    private WebSocketProvider webSocketProvider;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        log.info("msg={}", msg.text());
        ctx.channel().writeAndFlush(new TextWebSocketFrame("response:" + msg.text()));
        if ("close".equals(msg.text())) {
            ChannelFuture closeFuture = ctx.channel().close();
            closeFuture.addListener(
                    future -> log.info(LogFormat.builder().message("WebSocket is closed").build()));
        }
        Long userId = WebSocketUserHolder.getUserId(ctx.channel());
        TraceUtil.setTraceId(WebSocketHandler.getTrace(ctx.channel()).getTraceId());
        EnvUtil.setEnv(WebSocketHandler.getTrace(ctx.channel()).getEnv());
        webSocketProvider.handleMsg(userId, msg.text());
    }
}
