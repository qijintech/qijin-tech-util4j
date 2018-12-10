package tech.qijin.util4j.practice.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tech.qijin.util4j.practice.mapper.UserMapper;
import tech.qijin.util4j.practice.model.User;

import java.util.List;

/**
 * @author michealyang
 * @date 2018/11/28
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
public interface UserDao extends UserMapper {

    @Select("select * from user")
    List<User> getUsers();

    @Select("select * from user where id=#{id} and valid=1")
    List<User> getUser(@Param("id") int id);

    @Select("select u.* from user as u left join user_bag as ub on u.id=ub.user_id " +
            "where u.id=#{id} and ub.color=#{color} order by u.id")
    List<User> join(@Param("id") int id, @Param("color") int color);

}
