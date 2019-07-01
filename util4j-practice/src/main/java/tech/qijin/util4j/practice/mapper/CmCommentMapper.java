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
import tech.qijin.util4j.practice.model.CmComment;
import tech.qijin.util4j.practice.model.CmCommentExample;

public interface CmCommentMapper {
    @SelectProvider(type=CmCommentSqlProvider.class, method="countByExample")
    long countByExample(CmCommentExample example);

    @DeleteProvider(type=CmCommentSqlProvider.class, method="deleteByExample")
    int deleteByExample(CmCommentExample example);

    @Delete({
        "delete from cm_comment",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into cm_comment (user_id, base_id, ",
        "append_id, reply_id, ",
        "content, channel, ",
        "env, valid, ctime, ",
        "utime)",
        "values (#{userId,jdbcType=BIGINT}, #{baseId,jdbcType=BIGINT}, ",
        "#{appendId,jdbcType=BIGINT}, #{replyId,jdbcType=BIGINT}, ",
        "#{content,jdbcType=VARCHAR}, #{channel,jdbcType=TINYINT}, ",
        "#{env,jdbcType=INTEGER}, #{valid,jdbcType=INTEGER}, #{ctime,jdbcType=TIMESTAMP}, ",
        "#{utime,jdbcType=TIMESTAMP})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Long.class)
    int insert(CmComment record);

    @InsertProvider(type=CmCommentSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Long.class)
    int insertSelective(CmComment record);

    @SelectProvider(type=CmCommentSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.BIGINT),
        @Result(column="base_id", property="baseId", jdbcType=JdbcType.BIGINT),
        @Result(column="append_id", property="appendId", jdbcType=JdbcType.BIGINT),
        @Result(column="reply_id", property="replyId", jdbcType=JdbcType.BIGINT),
        @Result(column="content", property="content", jdbcType=JdbcType.VARCHAR),
        @Result(column="channel", property="channel", jdbcType=JdbcType.TINYINT),
        @Result(column="env", property="env", jdbcType=JdbcType.INTEGER),
        @Result(column="valid", property="valid", jdbcType=JdbcType.INTEGER),
        @Result(column="ctime", property="ctime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="utime", property="utime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<CmComment> selectByExample(CmCommentExample example);

    @Select({
        "select",
        "id, user_id, base_id, append_id, reply_id, content, channel, env, valid, ctime, ",
        "utime",
        "from cm_comment",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.BIGINT),
        @Result(column="base_id", property="baseId", jdbcType=JdbcType.BIGINT),
        @Result(column="append_id", property="appendId", jdbcType=JdbcType.BIGINT),
        @Result(column="reply_id", property="replyId", jdbcType=JdbcType.BIGINT),
        @Result(column="content", property="content", jdbcType=JdbcType.VARCHAR),
        @Result(column="channel", property="channel", jdbcType=JdbcType.TINYINT),
        @Result(column="env", property="env", jdbcType=JdbcType.INTEGER),
        @Result(column="valid", property="valid", jdbcType=JdbcType.INTEGER),
        @Result(column="ctime", property="ctime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="utime", property="utime", jdbcType=JdbcType.TIMESTAMP)
    })
    CmComment selectByPrimaryKey(Long id);

    @UpdateProvider(type=CmCommentSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") CmComment record, @Param("example") CmCommentExample example);

    @UpdateProvider(type=CmCommentSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") CmComment record, @Param("example") CmCommentExample example);

    @UpdateProvider(type=CmCommentSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(CmComment record);

    @Update({
        "update cm_comment",
        "set user_id = #{userId,jdbcType=BIGINT},",
          "base_id = #{baseId,jdbcType=BIGINT},",
          "append_id = #{appendId,jdbcType=BIGINT},",
          "reply_id = #{replyId,jdbcType=BIGINT},",
          "content = #{content,jdbcType=VARCHAR},",
          "channel = #{channel,jdbcType=TINYINT},",
          "env = #{env,jdbcType=INTEGER},",
          "valid = #{valid,jdbcType=INTEGER},",
          "ctime = #{ctime,jdbcType=TIMESTAMP},",
          "utime = #{utime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(CmComment record);
}