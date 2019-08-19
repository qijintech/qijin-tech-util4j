package tech.qijin.util4j.websocket.server;

import tech.qijin.util4j.websocket.server.handler.TextWebSocketFrameHandler;
import tech.qijin.util4j.websocket.server.handler.WebSocketHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author michealyang
 * @date 2019/1/24
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@Component
public class WebSocketServerInitializer extends ChannelInitializer<SocketChannel> {
    @Autowired
    private WebSocketHandler webSocketHandler;
    @Autowired
    private TextWebSocketFrameHandler textWebSocketFrameHandler;

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new ChunkedWriteHandler());
        pipeline.addLast(new HttpObjectAggregator(64 * 1024));
        pipeline.addLast(webSocketHandler);
        pipeline.addLast(textWebSocketFrameHandler);
    }
}
