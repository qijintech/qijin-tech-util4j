package tech.qijin.util4j.trace.util;

import tech.qijin.util4j.trace.pojo.Channel;

/**
 * @author michealyang
 * @date 2018/11/7
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
public class ChannelUtil {
    private static ThreadLocal<Channel> channelThreadLocal = new ThreadLocal();

    public static void setChannel(Channel channel) {
        channelThreadLocal.set(channel);
    }

    public static Channel getChannel() {
        return channelThreadLocal.get();
    }

    public static void remove() {
        channelThreadLocal.remove();
    }
}
