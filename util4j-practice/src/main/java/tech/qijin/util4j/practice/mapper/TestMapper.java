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
import tech.qijin.util4j.practice.model.Test;
import tech.qijin.util4j.practice.model.TestExample;

public interface TestMapper {
    @SelectProvider(type=TestSqlProvider.class, method="countByExample")
    long countByExample(TestExample example);

    @DeleteProvider(type=TestSqlProvider.class, method="deleteByExample")
    int deleteByExample(TestExample example);

    @Delete({
        "delete from test",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into test (col1, col2, ",
        "col3, env, valid, ",
        "ctime, utime)",
        "values (#{col1,jdbcType=INTEGER}, #{col2,jdbcType=VARCHAR}, ",
        "#{col3,jdbcType=DECIMAL}, #{env,jdbcType=TINYINT}, #{valid,jdbcType=TINYINT}, ",
        "#{ctime,jdbcType=TIMESTAMP}, #{utime,jdbcType=TIMESTAMP})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insert(Test record);

    @InsertProvider(type=TestSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insertSelective(Test record);

    @SelectProvider(type=TestSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="col1", property="col1", jdbcType=JdbcType.INTEGER),
        @Result(column="col2", property="col2", jdbcType=JdbcType.VARCHAR),
        @Result(column="col3", property="col3", jdbcType=JdbcType.DECIMAL),
        @Result(column="env", property="env", jdbcType=JdbcType.TINYINT),
        @Result(column="valid", property="valid", jdbcType=JdbcType.TINYINT),
        @Result(column="ctime", property="ctime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="utime", property="utime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<Test> selectByExample(TestExample example);

    @Select({
        "select",
        "id, col1, col2, col3, env, valid, ctime, utime",
        "from test",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="col1", property="col1", jdbcType=JdbcType.INTEGER),
        @Result(column="col2", property="col2", jdbcType=JdbcType.VARCHAR),
        @Result(column="col3", property="col3", jdbcType=JdbcType.DECIMAL),
        @Result(column="env", property="env", jdbcType=JdbcType.TINYINT),
        @Result(column="valid", property="valid", jdbcType=JdbcType.TINYINT),
        @Result(column="ctime", property="ctime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="utime", property="utime", jdbcType=JdbcType.TIMESTAMP)
    })
    Test selectByPrimaryKey(Integer id);

    @UpdateProvider(type=TestSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") Test record, @Param("example") TestExample example);

    @UpdateProvider(type=TestSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") Test record, @Param("example") TestExample example);

    @UpdateProvider(type=TestSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Test record);

    @Update({
        "update test",
        "set col1 = #{col1,jdbcType=INTEGER},",
          "col2 = #{col2,jdbcType=VARCHAR},",
          "col3 = #{col3,jdbcType=DECIMAL},",
          "env = #{env,jdbcType=TINYINT},",
          "valid = #{valid,jdbcType=TINYINT},",
          "ctime = #{ctime,jdbcType=TIMESTAMP},",
          "utime = #{utime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Test record);
}