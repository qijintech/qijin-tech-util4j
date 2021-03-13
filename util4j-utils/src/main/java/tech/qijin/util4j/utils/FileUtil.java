package tech.qijin.util4j.utils;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;
import java.util.Properties;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import tech.qijin.util4j.lang.constant.ResEnum;

/**
 * @author michealyang
 * @version 1.0
 * @created 18/6/11
 * 开始眼保健操： →_→  ↑_↑  ←_←  ↓_↓
 */
@Slf4j
public class FileUtil {
    /**
     * 把文件内容转化成json对象
     *
     * @param path
     * @return
     */
    public static JSONObject convertJson(String path) {
        try {
            StringBuffer buffer = new StringBuffer();
            BufferedReader bf = new BufferedReader(new FileReader(path));
            String s = null;
            while ((s = bf.readLine()) != null) {
                buffer.append(s.trim());
            }
            bf.close();
            String json = buffer.toString();
            JSONObject jsonObject = JSONObject.parseObject(json);
            return jsonObject;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
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

    // 文件名
    public static String prefix(String fileName) {
        return fileName.substring(0, fileName.lastIndexOf("."));
    }

    // 文件后缀
    public static String suffix(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }
}