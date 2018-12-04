package tech.qijin.util4j.practice.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tech.qijin.util4j.practice.model.User;

import java.util.List;

/**
 * @author michealyang
 * @date 2018/11/28
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
public interface UserDao {

    @Select("select * from user")
    List<User> getUsers();

    @Select("select * from user where id=#{id} and valid=1")
    List<User> getUser(@Param("id") int id);

}
