package tech.qijin.util4j.utils.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Assert;
import org.junit.Test;
import tech.qijin.util4j.utils.ValidationUtil;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author michealyang
 * @date 2019/1/14
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
public class ValidationUtilTest {
    @Data
    @AllArgsConstructor
    class Helper{
        @NotNull(message = "id不能为null")
        private Integer id;
        @NotEmpty
        private String name;
    }

    @Test
    public void validate() {
        Helper helper = new Helper(1, "asdf");
        ValidationUtil.validate(helper);
    }

    @Test
    public void password() {
        String password = "asdfk2jalsfjl";
        boolean res = ValidationUtil.password(password);
        Assert.assertTrue(res);
    }
}
