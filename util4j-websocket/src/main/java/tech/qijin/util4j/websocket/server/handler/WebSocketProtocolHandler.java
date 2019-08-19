package tech.qijin.util4j.websocket.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

import java.util.List;

/**
 * @author michealyang
 * @date 2019/1/26
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
public class WebSocketProtocolHandler extends MessageToMessageDecoder<WebSocketFrame> {
    private final boolean dropPongFrames;

    /**
     * Creates a new {@link io.netty.handler.codec.http.websocketx.WebSocketProtocolHandler} that will <i>drop</i> {@link PongWebSocketFrame}s.
     */
    WebSocketProtocolHandler() {
        this(true);
    }

    /**
     * Creates a new {@link io.netty.handler.codec.http.websocketx.WebSocketProtocolHandler}, given a parameter that determines whether or not to drop {@link
     * PongWebSocketFrame}s.
     *
     * @param dropPongFrames {@code true} if {@link PongWebSocketFrame}s should be dropped
     */
    WebSocketProtocolHandler(boolean dropPongFrames) {
        this.dropPongFrames = dropPongFrames;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, WebSocketFrame frame, List<Object> out) throws Exception {
        if (frame instanceof PingWebSocketFrame) {
            frame.content().retain();
            ctx.channel().writeAndFlush(new PongWebSocketFrame(frame.content()));
            return;
        }
        if (frame instanceof PongWebSocketFrame && dropPongFrames) {
            return;
        }

        out.add(frame.retain());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.fireExceptionCaught(cause);
        ctx.close();
    }
}
