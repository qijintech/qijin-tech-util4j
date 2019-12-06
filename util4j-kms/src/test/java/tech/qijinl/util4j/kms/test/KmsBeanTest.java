package tech.qijinl.util4j.kms.test;


import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.qijin.util4j.kms.KmsBean;

/**
 * @author michealyang
 * @date 2019-12-06
 * @relax: 开始眼保健操 ←_← ↓_↓ →_→ ↑_↑
 */
public class KmsBeanTest extends BaseTest {
    @Autowired
    private KmsBean kmsBean;

    @Test
    public void testGetSecretKey() {
        log.info("secretKey={}", kmsBean.getSecretKey("tencent"));
        log.info("secretKey={}", kmsBean.getSecretKey("ali"));
    }

    @Test
    public void testGetSecretId() {
        log.info("secretId={}", kmsBean.getSecretId("tencent"));
        log.info("secretId={}", kmsBean.getSecretId("ali"));
    }
}
