package tech.qijin.util4j.utils;

import lombok.extern.slf4j.Slf4j;
import tech.qijin.util4j.lang.constant.ResEnum;

import java.io.InputStream;
import java.net.URL;

/**
 * @author michealyang
 * @version 1.0
 * @created 18/6/11
 * 开始眼保健操： →_→  ↑_↑  ←_←  ↓_↓
 */
@Slf4j
public class FileUtil {

    public static String getFilePath(String fileName) {
        URL url = Thread.currentThread().getContextClassLoader().getResource(fileName);
        if (url == null) {
            return null;
        }
        return url.getPath();
    }

    /**
     * 从classpath中读取文件
     *
     * @param fileName
     * @return
     */
    public static InputStream readFileFromClasspath(String fileName) {
        MAssert.notBlank(fileName, ResEnum.INVALID_PARAM);
        InputStream in = FileUtil.class.getClassLoader().getResourceAsStream(fileName);
        if (in == null) {
            log.error(LogFormat.builder().message("file not found from classpath")
                    .put("fileName", fileName).build());
        }
        return in;
    }
}