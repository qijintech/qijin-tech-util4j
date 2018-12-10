package tech.qijin.util4j.practice.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.qijin.util4j.practice.model.User;
import tech.qijin.util4j.practice.pojo.ColorEnm;
import tech.qijin.util4j.practice.service.UserService;

/**
 * @author michealyang
 * @date 2018/12/7
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
public class UserServiceTest extends TestServerApplicationTests {
    @Autowired
    private UserService userService;

    @Test
    public void getUsers() {
        userService.getUsers();
    }

    @Test
    public void getUser() {
        userService.getUser(1);
    }

    @Test
    public void insert() {
        User user = new User();
        user.setName("micheal");
        user.setUserName("user micheal");
        userService.insert(user);
    }

    @Test
    public void update() {
        userService.update("update");
    }

    @Test
    public void join() {
        userService.join(1, ColorEnm.BLUE);
    }
}
