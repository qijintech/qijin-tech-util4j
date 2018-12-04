package tech.qijin.util4j.web.util;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * @author michealyang
 * @date 2018/11/13
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
public class LogFormat {
    public static LogFormatBuilder builder() {
        return new LogFormatBuilder();
    }

    public static class LogFormatBuilder extends tech.qijin.util4j.utils.log.LogFormat.LogFormatBuilder {
        private String traceId;

        public LogFormatBuilder() {
            this.traceId = TraceUtil.getTraceId();
        }

        @Override
        public String build() {
            StringBuilder sb = new StringBuilder();
            sb.append("[").append(traceId).append("] ");
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
        TraceUtil.setTraceId();
        String res = LogFormat.builder()
                .put("test", 1)
                .put("hehe", null)
                .message("this is a test")
                .build();
        System.out.println(res);
    }
}
