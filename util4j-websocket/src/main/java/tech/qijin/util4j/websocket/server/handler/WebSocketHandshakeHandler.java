package tech.qijin.util4j.websocket.server.handler;

import tech.qijin.util4j.utils.LogFormat;
import tech.qijin.util4j.websocket.server.WebSocketUserHolder;
import tech.qijin.util4j.websocket.spi.WebSocketOfflineService;
import tech.qijin.util4j.websocket.spi.WebSocketProvider;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.handler.ssl.SslHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

import static io.netty.handler.codec.http.HttpMethod.GET;
import static io.netty.handler.codec.http.HttpResponseStatus.FORBIDDEN;
import static io.netty.handler.codec.http.HttpResponseStatus.UNAUTHORIZED;
import static io.netty.handler.codec.http.HttpUtil.isKeepAlive;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @author michealyang
 * @date 2019/1/26
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@Slf4j
public class WebSocketHandshakeHandler extends ChannelInboundHandlerAdapter {

    private final String websocketPath;
    private final String subprotocols;
    private final boolean allowExtensions;
    private final int maxFramePayloadSize;
    private final boolean allowMaskMismatch;
    private final boolean checkStartsWith;
    private WebSocketProvider webSocketProvider;
    private WebSocketOfflineService webSocketOfflineService;

    private static final String TOKEN = "token";


    WebSocketHandshakeHandler(WebSocketProvider webSocketProvider,
                              WebSocketOfflineService webSocketOfflineService,
                              String websocketPath,
                              String subprotocols,
                              boolean allowExtensions,
                              int maxFrameSize,
                              boolean allowMaskMismatch,
                              boolean checkStartsWith) {

        this.webSocketProvider = webSocketProvider;
        this.webSocketOfflineService = webSocketOfflineService;
        this.websocketPath = websocketPath;
        this.subprotocols = subprotocols;
        this.allowExtensions = allowExtensions;
        maxFramePayloadSize = maxFrameSize;
        this.allowMaskMismatch = allowMaskMismatch;
        this.checkStartsWith = checkStartsWith;
    }

    @Override
    public void channelRead(final ChannelHandlerContext ctx, Object msg) throws Exception {
        final FullHttpRequest req = (FullHttpRequest) msg;
        if (isNotWebSocketPath(req)) {
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HTTP_1_1, FORBIDDEN));
            return;
        }

        try {
            if (req.method() != GET) {
                sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HTTP_1_1, FORBIDDEN));
                return;
            }

            String token = getToken(req);
            if (StringUtils.isBlank(token)) {
                log.warn(LogFormat.builder().message("token is null").build());
                sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HTTP_1_1, UNAUTHORIZED));
                return;
            }
            WebSocketHandler.setTrace(ctx.channel(), req);
            Long uid = webSocketProvider.authToken(token);

            final WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(
                    getWebSocketLocation(ctx.pipeline(), req, websocketPath), subprotocols,
                    allowExtensions, maxFramePayloadSize, allowMaskMismatch);
            final WebSocketServerHandshaker handshaker = wsFactory.newHandshaker(req);
            if (handshaker == null) {
                WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
            } else {
                final ChannelFuture handshakeFuture = handshaker.handshake(ctx.channel(), req);
                handshakeFuture.addListener(future -> {
                    if (!future.isSuccess()) {
                        ctx.fireExceptionCaught(future.cause());
                    } else {
                        // Kept for compatibility
                        ctx.fireUserEventTriggered(
                                WebSocketHandler.ServerHandshakeStateEvent.HANDSHAKE_COMPLETE);
                        ctx.fireUserEventTriggered(
                                new WebSocketHandler.HandshakeComplete(
                                        req.uri(), req.headers(), handshaker.selectedSubprotocol()));
                    }
                });

                WebSocketHandler.setHandshaker(ctx.channel(), handshaker);
                WebSocketUserHolder.add(uid, ctx.channel());
                webSocketOfflineService.pushOfflineMsg(uid);
                ctx.pipeline().replace(this, "WS403Responder",
                        WebSocketHandler.forbiddenHttpRequestResponder());
            }
        } finally {
            req.release();
        }
    }

    private boolean isNotWebSocketPath(FullHttpRequest req) {
        return checkStartsWith ? !req.uri().startsWith(websocketPath) : !req.uri().equals(websocketPath);
    }

    private static void sendHttpResponse(ChannelHandlerContext ctx, HttpRequest req, HttpResponse res) {
        ChannelFuture f = ctx.channel().writeAndFlush(res);
        if (!isKeepAlive(req) || res.status().code() != 200) {
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }

    private static String getWebSocketLocation(ChannelPipeline cp, HttpRequest req, String path) {
        String protocol = "ws";
        if (cp.get(SslHandler.class) != null) {
            // SSL in use so use Secure WebSockets
            protocol = "wss";
        }
        String host = req.headers().get(HttpHeaderNames.HOST);
        return protocol + "://" + host + path;
    }

    private String getToken(FullHttpRequest request) {
        QueryStringDecoder queryStringDecoder = new QueryStringDecoder(request.uri());
        Map<String, List<String>> parameters = queryStringDecoder.parameters();
        if (parameters.size() == 0 || !parameters.containsKey(TOKEN)) {
            return null;
        }
        return parameters.get(TOKEN).get(0);
    }
}
