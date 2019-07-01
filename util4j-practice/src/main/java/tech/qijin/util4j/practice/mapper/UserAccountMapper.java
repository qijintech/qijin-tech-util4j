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
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import tech.qijin.util4j.practice.model.UserAccount;
import tech.qijin.util4j.practice.model.UserAccountExample;

public interface UserAccountMapper {
    @SelectProvider(type=UserAccountSqlProvider.class, method="countByExample")
    long countByExample(UserAccountExample example);

    @DeleteProvider(type=UserAccountSqlProvider.class, method="deleteByExample")
    int deleteByExample(UserAccountExample example);

    @Delete({
        "delete from user_account",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into user_account (user_name, password, ",
        "source, openid, ",
        "channel, env, valid, ",
        "ctime, utime)",
        "values (#{userName,jdbcType=VARCHAR}, #{password,jdbcType=VARCHAR}, ",
        "#{source,jdbcType=TINYINT}, #{openid,jdbcType=VARCHAR}, ",
        "#{channel,jdbcType=TINYINT}, #{env,jdbcType=TINYINT}, #{valid,jdbcType=TINYINT}, ",
        "#{ctime,jdbcType=TIMESTAMP}, #{utime,jdbcType=TIMESTAMP})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Long.class)
    int insert(UserAccount record);

    @InsertProvider(type=UserAccountSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Long.class)
    int insertSelective(UserAccount record);

    @SelectProvider(type=UserAccountSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="user_name", property="userName", jdbcType=JdbcType.VARCHAR),
        @Result(column="password", property="password", jdbcType=JdbcType.VARCHAR),
        @Result(column="source", property="source", jdbcType=JdbcType.TINYINT),
        @Result(column="openid", property="openid", jdbcType=JdbcType.VARCHAR),
        @Result(column="channel", property="channel", jdbcType=JdbcType.TINYINT),
        @Result(column="env", property="env", jdbcType=JdbcType.TINYINT),
        @Result(column="valid", property="valid", jdbcType=JdbcType.TINYINT),
        @Result(column="ctime", property="ctime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="utime", property="utime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<UserAccount> selectByExample(UserAccountExample example);

    @Select({
        "select",
        "id, user_name, password, source, openid, channel, env, valid, ctime, utime",
        "from user_account",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="user_name", property="userName", jdbcType=JdbcType.VARCHAR),
        @Result(column="password", property="password", jdbcType=JdbcType.VARCHAR),
        @Result(column="source", property="source", jdbcType=JdbcType.TINYINT),
        @Result(column="openid", property="openid", jdbcType=JdbcType.VARCHAR),
        @Result(column="channel", property="channel", jdbcType=JdbcType.TINYINT),
        @Result(column="env", property="env", jdbcType=JdbcType.TINYINT),
        @Result(column="valid", property="valid", jdbcType=JdbcType.TINYINT),
        @Result(column="ctime", property="ctime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="utime", property="utime", jdbcType=JdbcType.TIMESTAMP)
    })
    UserAccount selectByPrimaryKey(Long id);

    @UpdateProvider(type=UserAccountSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") UserAccount record, @Param("example") UserAccountExample example);

    @UpdateProvider(type=UserAccountSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") UserAccount record, @Param("example") UserAccountExample example);

    @UpdateProvider(type=UserAccountSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(UserAccount record);

    @Update({
        "update user_account",
        "set user_name = #{userName,jdbcType=VARCHAR},",
          "password = #{password,jdbcType=VARCHAR},",
          "source = #{source,jdbcType=TINYINT},",
          "openid = #{openid,jdbcType=VARCHAR},",
          "channel = #{channel,jdbcType=TINYINT},",
          "env = #{env,jdbcType=TINYINT},",
          "valid = #{valid,jdbcType=TINYINT},",
          "ctime = #{ctime,jdbcType=TIMESTAMP},",
          "utime = #{utime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(UserAccount record);
}