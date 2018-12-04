package tech.qijin.util4j.web.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class HttpJsonUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpJsonUtil.class);


    private static ObjectMapper mapper;

    /**
     * 静态代码块，初始化mapper
     */
    static {
        mapper = Jackson2ObjectMapperBuilder.json().simpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,
                        DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                // 允许转义
                .featuresToEnable(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS).build();
    }


    public static void writeStringAsJson(HttpServletResponse response, Object result) {
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        PrintWriter pw = null;
        try {
            pw = response.getWriter();
            pw.write(mapper.writeValueAsString(result));
        } catch (Exception e) {
            LOGGER.error("writeStringAsJson has error : ", e);
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }
}
