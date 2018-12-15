package tech.qijin.util4j.aop.annotation;

/**
 * @author yangshangqiang
 * @date 2018/11/5
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
public enum FailurePolicy {
    //失败后抛出异常
    EXCPTION,
    //失败后将参数放入mq中
    MQ;
}
