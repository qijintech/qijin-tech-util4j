package tech.qijin.util4j.websocket.util;

import tech.qijin.util4j.utils.LogFormat;
import tech.qijin.util4j.websocket.server.WebSocketUserHolder;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

/**
 * @author michealyang
 * @date 2019/1/29
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@Slf4j
public class WebsocketUtil {
    /**
     * 检查用户是否在线
     *
     * @param userId
     * @return
     */
    public static boolean exist(Long userId) {
        return WebSocketUserHolder.getChannel(userId).isPresent();
    }

    public static boolean send(Long userId, String msg) {
        Optional<Channel> channelOpt = WebSocketUserHolder.getChannel(userId);
        return channelOpt.map(channel -> {
            TextWebSocketFrame textFrame = new TextWebSocketFrame(msg);
            channel.writeAndFlush(textFrame);
            log.info(LogFormat.builder().message("msg sent through websocket")
                    .put("userId", userId).put("msg", msg).build());
            return true;
        }).orElseGet(() -> {
            log.info(LogFormat.builder().message("no user found")
                    .put("userId", userId).build());
            return false;
        });
    }

    public static boolean broadcast(String msg) {
        List<Channel> channelList = WebSocketUserHolder.getAllChannel();
        log.info(LogFormat.builder().message("broadcast msg through websocket")
                .put("channelList size", channelList.size()).put("msg", msg).build());
        // todo 应多线程推送
        for (Channel channel : channelList) {
            TextWebSocketFrame textFrame = new TextWebSocketFrame(msg);
            channel.writeAndFlush(textFrame);
        }
        return true;
    }
}
