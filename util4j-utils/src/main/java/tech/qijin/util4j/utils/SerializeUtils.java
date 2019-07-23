package tech.qijin.util4j.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class SerializeUtils {
    /**
     * 隐藏构造
     */
    private SerializeUtils() {

    }

    /**
     * 序列化
     *
     * @param object
     * @return
     * @throws Exception
     */
    public static byte[] serialize(Object object) throws IOException {
        if (object == null) {
            return new byte[0];

        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(object);
        return baos.toByteArray();
    }

    /**
     * 反序列化
     *
     * @param bytes
     * @return
     * @throws Exception
     */
    public static Object unSerialize(byte[] bytes) throws Exception {
        if (bytes == null) {
            throw new NullPointerException("bytes is null");
        }
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(bais);
        return ois.readObject();
    }

}
