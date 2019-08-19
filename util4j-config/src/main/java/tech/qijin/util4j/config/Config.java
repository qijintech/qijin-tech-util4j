package tech.qijin.util4j.config;

/**
 * @author michealyang
 * @date 2019/3/8
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
public interface Config {
    /**
     * 获取统一配置的域名
     *
     * @return
     */
    String host();

    String module();

    String configPath();

    /**
     * 是否配置了<code>debug=true</code>字段
     *
     * <p>通过这个配置，可及时开启和关闭部分日志打印。</p>
     *
     * <p>
     * 使用方法为:
     * <pre>
     *     if(config.debugEnabled){
     *         log.info("message")
     *     }
     * </pre>
     * </p>
     *
     * @return
     */
    Boolean debugEnabled();

    /**
     * 是否配置了<code>multi=true</code>字段
     * <p>
     * 配置后，可以提供将日志打印到多个log的能力
     * </p>
     *
     * <pre>
     *     log.info(...);
     *     if(config.multiLog()){
     *         //可配置log1出入日志到特定文件
     *         log1.info(....)
     *     }
     * </pre>
     *
     * @return
     */
    Boolean multiLog();

    /**
     * 是否打印更多的日志开关
     *
     * <pre>
     *     LogFormat.Builder builder = ...;
     *     if(config.moreLog()){
     *         builder.put(...)
     *         builder.put(...)
     *     }
     *     log.info(builder.build());
     * </pre>
     *
     * @return
     */
    Boolean moreLog();

    String get(String key, String defaultValue);
}
