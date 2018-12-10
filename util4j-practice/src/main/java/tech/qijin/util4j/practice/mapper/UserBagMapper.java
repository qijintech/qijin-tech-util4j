package tech.qijin.util4j.practice.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import tech.qijin.util4j.practice.model.UserBag;
import tech.qijin.util4j.practice.model.UserBagExample;

public interface UserBagMapper {
    @SelectProvider(type=UserBagSqlProvider.class, method="countByExample")
    long countByExample(UserBagExample example);

    @DeleteProvider(type=UserBagSqlProvider.class, method="deleteByExample")
    int deleteByExample(UserBagExample example);

    @Delete({
        "delete from user_bag",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into user_bag (id, user_id, ",
        "name, color)",
        "values (#{id,jdbcType=INTEGER}, #{userId,jdbcType=INTEGER}, ",
        "#{name,jdbcType=VARCHAR}, #{color,jdbcType=TINYINT})"
    })
    int insert(UserBag record);

    @InsertProvider(type=UserBagSqlProvider.class, method="insertSelective")
    int insertSelective(UserBag record);

    @SelectProvider(type=UserBagSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.INTEGER),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="color", property="color", jdbcType=JdbcType.TINYINT)
    })
    List<UserBag> selectByExample(UserBagExample example);

    @Select({
        "select",
        "id, user_id, name, color",
        "from user_bag",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.INTEGER),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="color", property="color", jdbcType=JdbcType.TINYINT)
    })
    UserBag selectByPrimaryKey(Integer id);

    @UpdateProvider(type=UserBagSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") UserBag record, @Param("example") UserBagExample example);

    @UpdateProvider(type=UserBagSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") UserBag record, @Param("example") UserBagExample example);

    @UpdateProvider(type=UserBagSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(UserBag record);

    @Update({
        "update user_bag",
        "set user_id = #{userId,jdbcType=INTEGER},",
          "name = #{name,jdbcType=VARCHAR},",
          "color = #{color,jdbcType=TINYINT}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(UserBag record);
}