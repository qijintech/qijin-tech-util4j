package tech.qijin.util4j.cache.test;

import com.google.common.collect.Lists;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author michealyang
 * @date 2019/1/21
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@Data
public class BClass implements Serializable {
    private static final long serialVersionUID = 4825687021287472225L;
    private List<Integer> b1 = Lists.newArrayList(1, 2);
}
