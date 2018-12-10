package tech.qijin.util4j.practice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.qijin.util4j.practice.dao.UserDao;
import tech.qijin.util4j.practice.model.User;
import tech.qijin.util4j.practice.model.UserExample;
import tech.qijin.util4j.practice.pojo.ColorEnm;

import java.util.List;

/**
 * @author michealyang
 * @date 2018/11/28
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public List<User> getUsers() {
        return userDao.getUsers();
    }

    @Transactional
    public List<User> getUser(int id) {
        return userDao.getUser(id);
    }

    public int insert(User user) {
        return userDao.insertSelective(user);
    }

    public void update(String name) {
        User record = new User();
        record.setName(name);
        UserExample example = new UserExample();
        example.or().andIdEqualTo(1);
        userDao.updateByExampleSelective(record, example);
    }

    public List<User> join(int uid, ColorEnm colorEnm) {
        return userDao.join(uid, colorEnm.value());
    }
}
