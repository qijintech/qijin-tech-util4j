package tech.qijin.util4j.audit;

import java.util.Objects;

import tech.qijin.util4j.encryption.EncriptUtil;

/**
 * 审计原理
 * <p>将要存储的某些重要字段，如金额、用户id等作为参数，计算其签名值，然后存储。
 * 审计时，再使用相同的字段计算签名，比较两次签名的异同</p>
 *
 * @author michealyang
 * @date 2018/11/30
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
public class AuditUtil {
    //质数
    private static final int CHECKSUM_SALT = 89885989;

    /**
     * 审计账户合法性
     *
     * @param signature 之前存储的签名值
     * @param fields    被用作签名的一些属性
     * @return
     */
    public static boolean doAudit(String signature, Object... fields) {
        return generateSignature(fields).equals(signature);
    }

    /**
     * 生成一组数据的签名
     *
     * @param fields 参数必须重写toString方法
     * @return
     */
    private static String generateSignature(Object... fields) {
        StringBuilder sb = new StringBuilder();
        for (Object obj : fields) {
            sb.append(obj);
        }
        sb.append(checksum(fields));

        //计算MD5
        return EncriptUtil.md5Encode(sb.toString().getBytes());
    }

    /**
     * 生成一组数据的校验和
     *
     * @param objects
     * @return
     */
    private static int checksum(Object... objects) {
        int hashCode = Objects.hash(objects);

        return hashCode ^ CHECKSUM_SALT;
    }
}
