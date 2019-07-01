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
import tech.qijin.util4j.practice.model.FaFavorites;
import tech.qijin.util4j.practice.model.FaFavoritesExample;

public interface FaFavoritesMapper {
    @SelectProvider(type=FaFavoritesSqlProvider.class, method="countByExample")
    long countByExample(FaFavoritesExample example);

    @DeleteProvider(type=FaFavoritesSqlProvider.class, method="deleteByExample")
    int deleteByExample(FaFavoritesExample example);

    @Delete({
        "delete from fa_favorites",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into fa_favorites (user_id, item_id, ",
        "collect, valid, ",
        "env, channel, version, ",
        "ctime, utime)",
        "values (#{userId,jdbcType=BIGINT}, #{itemId,jdbcType=BIGINT}, ",
        "#{collect,jdbcType=TINYINT}, #{valid,jdbcType=TINYINT}, ",
        "#{env,jdbcType=TINYINT}, #{channel,jdbcType=TINYINT}, #{version,jdbcType=BIGINT}, ",
        "#{ctime,jdbcType=TIMESTAMP}, #{utime,jdbcType=TIMESTAMP})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Long.class)
    int insert(FaFavorites record);

    @InsertProvider(type=FaFavoritesSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Long.class)
    int insertSelective(FaFavorites record);

    @SelectProvider(type=FaFavoritesSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.BIGINT),
        @Result(column="item_id", property="itemId", jdbcType=JdbcType.BIGINT),
        @Result(column="collect", property="collect", jdbcType=JdbcType.TINYINT),
        @Result(column="valid", property="valid", jdbcType=JdbcType.TINYINT),
        @Result(column="env", property="env", jdbcType=JdbcType.TINYINT),
        @Result(column="channel", property="channel", jdbcType=JdbcType.TINYINT),
        @Result(column="version", property="version", jdbcType=JdbcType.BIGINT),
        @Result(column="ctime", property="ctime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="utime", property="utime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<FaFavorites> selectByExample(FaFavoritesExample example);

    @Select({
        "select",
        "id, user_id, item_id, collect, valid, env, channel, version, ctime, utime",
        "from fa_favorites",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.BIGINT),
        @Result(column="item_id", property="itemId", jdbcType=JdbcType.BIGINT),
        @Result(column="collect", property="collect", jdbcType=JdbcType.TINYINT),
        @Result(column="valid", property="valid", jdbcType=JdbcType.TINYINT),
        @Result(column="env", property="env", jdbcType=JdbcType.TINYINT),
        @Result(column="channel", property="channel", jdbcType=JdbcType.TINYINT),
        @Result(column="version", property="version", jdbcType=JdbcType.BIGINT),
        @Result(column="ctime", property="ctime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="utime", property="utime", jdbcType=JdbcType.TIMESTAMP)
    })
    FaFavorites selectByPrimaryKey(Long id);

    @UpdateProvider(type=FaFavoritesSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") FaFavorites record, @Param("example") FaFavoritesExample example);

    @UpdateProvider(type=FaFavoritesSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") FaFavorites record, @Param("example") FaFavoritesExample example);

    @UpdateProvider(type=FaFavoritesSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(FaFavorites record);

    @Update({
        "update fa_favorites",
        "set user_id = #{userId,jdbcType=BIGINT},",
          "item_id = #{itemId,jdbcType=BIGINT},",
          "collect = #{collect,jdbcType=TINYINT},",
          "valid = #{valid,jdbcType=TINYINT},",
          "env = #{env,jdbcType=TINYINT},",
          "channel = #{channel,jdbcType=TINYINT},",
          "version = #{version,jdbcType=BIGINT},",
          "ctime = #{ctime,jdbcType=TIMESTAMP},",
          "utime = #{utime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(FaFavorites record);
}