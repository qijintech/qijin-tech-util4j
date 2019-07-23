package tech.qijin.util4j.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;
import java.util.Properties;

import lombok.extern.slf4j.Slf4j;
import tech.qijin.util4j.lang.constant.ResEnum;

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
    public static InputStream getInputStreamFromClasspath(String fileName) {
        MAssert.notBlank(fileName, ResEnum.INVALID_PARAM);
        InputStream in = FileUtil.class.getClassLoader().getResourceAsStream(fileName);
        if (in == null) {
            log.error(LogFormat.builder().message("file not found from classpath")
                    .put("fileName", fileName).build());
        }
        return in;
    }

    public static FileInputStream getFileInputStreamFromPath(String fileName) throws FileNotFoundException {
        MAssert.notBlank(fileName, ResEnum.INVALID_PARAM);
        File file = new File(fileName);
        if (!file.exists()) {
            return null;
        }
        return new FileInputStream(file);
    }

    public static FileInputStream getFileInputStreamFromClasspath(String fileName) throws URISyntaxException, FileNotFoundException {
        MAssert.notBlank(fileName, ResEnum.INVALID_PARAM);
        File file = new File(FileUtil.class.getClassLoader().getResource(fileName).toURI());
        if (file == null) {
            log.error(LogFormat.builder().message("file not found from classpath")
                    .put("fileName", fileName).build());
        }
        return new FileInputStream(file);
    }

    public static Optional<Properties> readPropertiesFromClasspath(String fileName) throws IOException, URISyntaxException {
        try {
            Properties properties = new Properties();
            InputStream in = getInputStreamFromClasspath(fileName);
            if (in == null) {
                log.warn(LogFormat.builder().message("file not found in path").put("path", fileName).build());
                return Optional.empty();
            }
            properties.load(in);
            return Optional.of(properties);
        } catch (Exception e) {
            log.warn(LogFormat.builder().message("file not found in path").put("path", fileName).build(), e);
            return Optional.empty();
        }
    }

    public static Optional<Properties> readPropertiesFromPath(String fileName) throws IOException {
        try {
            Properties properties = new Properties();
            InputStream in = getFileInputStreamFromPath(fileName);
            if (in == null) {
                log.warn(LogFormat.builder().message("file not found in classpath").put("path", fileName).build());
                return Optional.empty();
            }
            properties.load(in);
            return Optional.of(properties);
        } catch (Exception e) {
            log.warn(LogFormat.builder().message("file not found in classpath").put("path", fileName).build(), e);
            return Optional.empty();
        }
    }
}