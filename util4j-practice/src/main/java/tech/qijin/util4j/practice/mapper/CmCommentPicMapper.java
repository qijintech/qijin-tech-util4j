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
import tech.qijin.util4j.practice.model.CmCommentPic;
import tech.qijin.util4j.practice.model.CmCommentPicExample;

public interface CmCommentPicMapper {
    @SelectProvider(type=CmCommentPicSqlProvider.class, method="countByExample")
    long countByExample(CmCommentPicExample example);

    @DeleteProvider(type=CmCommentPicSqlProvider.class, method="deleteByExample")
    int deleteByExample(CmCommentPicExample example);

    @Delete({
        "delete from cm_comment_pic",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into cm_comment_pic (comment_id, pic, ",
        "channel, env, valid, ",
        "ctime, utime)",
        "values (#{commentId,jdbcType=BIGINT}, #{pic,jdbcType=VARCHAR}, ",
        "#{channel,jdbcType=INTEGER}, #{env,jdbcType=INTEGER}, #{valid,jdbcType=INTEGER}, ",
        "#{ctime,jdbcType=TIMESTAMP}, #{utime,jdbcType=TIMESTAMP})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insert(CmCommentPic record);

    @InsertProvider(type=CmCommentPicSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insertSelective(CmCommentPic record);

    @SelectProvider(type=CmCommentPicSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="comment_id", property="commentId", jdbcType=JdbcType.BIGINT),
        @Result(column="pic", property="pic", jdbcType=JdbcType.VARCHAR),
        @Result(column="channel", property="channel", jdbcType=JdbcType.INTEGER),
        @Result(column="env", property="env", jdbcType=JdbcType.INTEGER),
        @Result(column="valid", property="valid", jdbcType=JdbcType.INTEGER),
        @Result(column="ctime", property="ctime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="utime", property="utime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<CmCommentPic> selectByExample(CmCommentPicExample example);

    @Select({
        "select",
        "id, comment_id, pic, channel, env, valid, ctime, utime",
        "from cm_comment_pic",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="comment_id", property="commentId", jdbcType=JdbcType.BIGINT),
        @Result(column="pic", property="pic", jdbcType=JdbcType.VARCHAR),
        @Result(column="channel", property="channel", jdbcType=JdbcType.INTEGER),
        @Result(column="env", property="env", jdbcType=JdbcType.INTEGER),
        @Result(column="valid", property="valid", jdbcType=JdbcType.INTEGER),
        @Result(column="ctime", property="ctime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="utime", property="utime", jdbcType=JdbcType.TIMESTAMP)
    })
    CmCommentPic selectByPrimaryKey(Integer id);

    @UpdateProvider(type=CmCommentPicSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") CmCommentPic record, @Param("example") CmCommentPicExample example);

    @UpdateProvider(type=CmCommentPicSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") CmCommentPic record, @Param("example") CmCommentPicExample example);

    @UpdateProvider(type=CmCommentPicSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(CmCommentPic record);

    @Update({
        "update cm_comment_pic",
        "set comment_id = #{commentId,jdbcType=BIGINT},",
          "pic = #{pic,jdbcType=VARCHAR},",
          "channel = #{channel,jdbcType=INTEGER},",
          "env = #{env,jdbcType=INTEGER},",
          "valid = #{valid,jdbcType=INTEGER},",
          "ctime = #{ctime,jdbcType=TIMESTAMP},",
          "utime = #{utime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(CmCommentPic record);
}