package tech.qijin.util4j.websocket.spi;

/**
 * @author michealyang
 * @date 2019/1/24
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
public interface WebSocketProvider {
    /**
     *
     * @param token
     * @return
     */
    Long authToken(String token);

    /**
     * 处理消息
     *
     * @param userId
     * @param msg
     */
    void handleMsg(Long userId, String msg);

    /**
     * 用户下线
     * @param userId
     */
    void userOffline(Long userId);
}
