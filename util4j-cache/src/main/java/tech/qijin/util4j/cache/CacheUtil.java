package tech.qijin.util4j.cache;

/**
 * @author michealyang
 * @date 2019/1/11
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
public class CacheUtil implements ICache {
    private ICache cacheApi;

    public void setCacheApi(ICache cacheApi) {
        this.cacheApi = cacheApi;
    }
}
