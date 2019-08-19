package tech.qijin.util4j.websocket.spi;

import java.util.List;

/**
 * @author michealyang
 * @date 2019/5/5
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
public interface WebSocketOfflineService {
    /**
     * 存储离线数据
     *
     * @param wrapper
     */
    void saveOfflineMsg(Object wrapper);

    /**
     * 拉取离线数据
     *
     * @param userId
     */
    List<String> getOfflineMsg(Long userId);

    /**
     * 向用户下发离线数据
     *
     * @param userId
     */
    void pushOfflineMsg(Long userId);
}
