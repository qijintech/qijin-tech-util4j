package tech.qijin.util4j.utils.log;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import tech.qijin.util4j.trace.util.TraceUtil;

import java.util.Map;

/**
 * @author michealyang
 * @date 2018/11/13
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
public class LogFormat {
    public static LogFormatBuilder builder(){
        return new LogFormatBuilder();
    }

    public static class LogFormatBuilder {
        protected Map<String, Object> container = Maps.newHashMap();
        protected String message;

        private String traceId;

        public LogFormatBuilder() {
            this.traceId = TraceUtil.getTraceId();
        }

        public LogFormatBuilder put(String key, Object value){
            if (StringUtils.isBlank(key)) {
                return this;
            }
            container.put(key, value);
            return this;
        }

        public LogFormatBuilder message(String value) {
            message = value;
            return this;
        }

        public LogFormatBuilder put(Map<String, Object> values){
            container.putAll(values);
            return this;
        }

        public String build() {
            StringBuilder sb = new StringBuilder();
            if (traceId != null) {
                sb.append("[").append(traceId).append("] ");
            }
            if (StringUtils.isNotBlank(message)) {
                sb.append(message).append(" ");
            }
            for (Map.Entry entry : container.entrySet()) {
                sb.append("{");
                sb.append(entry.getKey());
                sb.append("=");
                sb.append(entry.getValue() == null ? "null" : entry.getValue().toString());
                sb.append("}");
            }
            return sb.toString();
        }
    }

    public static void main(String[] args) {
        String res = LogFormat.builder()
                .put("test", 1)
                .put("hehe", null)
                .message("this is a test")
                .build();
        System.out.println(res);
    }
}
