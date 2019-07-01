package tech.qijin.util4j.practice;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author michealyang
 * @date 2019/4/18
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@Data
@EqualsAndHashCode()
public class Project {
    private Long id;

    public Project(Long id) {
        this.id = id;
    }

    private static final Project defaults = new Project(10L);
}
