package tech.qijin.util4j.lang.constant;

import java.util.List;

import com.google.common.base.Preconditions;

/**
 * @author michealyang
 * @date 2019/4/7
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
public interface EnumStatus<T extends EnumStatus> extends EnumValue {
    /**
     * 当前状态机可流转的下游状态
     *
     * @return
     */
    List<T> next();

    /**
     * 是否可流转到指定状态机
     *
     * @param to
     * @return
     */
    default boolean isFlowableTo(T to) {
        Preconditions.checkArgument(to != null);
        return this.next().contains(to);
    }
}
