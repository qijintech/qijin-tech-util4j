package tech.qijin.util4j.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 针对参数只有一个，且为List或Set的情况，省去入参判空的步骤
 * 如：
 * <pre>
 * public List<String> func(List<Long> ids){
 *     if(CollectionUtils.isEmpty(ids)){
 *         return Collections.emptyList();
 *     }
 * }
 *
 * 这个步骤可以省去，直接使用此注解即可。
 *
 * </pre>
 *
 * <p>
 * 注意适用场景：
 * <ul>
 * <li>1. 入参只能是一个</li>
 * <li>2. 入参只能是List或Set</li>
 * <li>3. 返回结果只能是List、Map、Set</li>
 * </ul>
 * </p>
 *
 * @author yangshangqiang
 * @date 2018/11/2
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@Target(ElementType.METHOD) //用{}包围，逗号分隔
@Retention(RetentionPolicy.RUNTIME)
public @interface ListCheck {
}
