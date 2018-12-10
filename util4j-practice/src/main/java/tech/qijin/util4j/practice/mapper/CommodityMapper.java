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
import tech.qijin.util4j.practice.model.Commodity;
import tech.qijin.util4j.practice.model.CommodityExample;

public interface CommodityMapper {
    @SelectProvider(type=CommoditySqlProvider.class, method="countByExample")
    long countByExample(CommodityExample example);

    @DeleteProvider(type=CommoditySqlProvider.class, method="deleteByExample")
    int deleteByExample(CommodityExample example);

    @Delete({
        "delete from commodity",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into commodity (title, brand, ",
        "price, quatity, ",
        "pic, slide, valid, ",
        "ctime, utime)",
        "values (#{title,jdbcType=VARCHAR}, #{brand,jdbcType=VARCHAR}, ",
        "#{price,jdbcType=INTEGER}, #{quatity,jdbcType=INTEGER}, ",
        "#{pic,jdbcType=VARCHAR}, #{slide,jdbcType=VARCHAR}, #{valid,jdbcType=TINYINT}, ",
        "#{ctime,jdbcType=TIMESTAMP}, #{utime,jdbcType=TIMESTAMP})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insert(Commodity record);

    @InsertProvider(type=CommoditySqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insertSelective(Commodity record);

    @SelectProvider(type=CommoditySqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="title", property="title", jdbcType=JdbcType.VARCHAR),
        @Result(column="brand", property="brand", jdbcType=JdbcType.VARCHAR),
        @Result(column="price", property="price", jdbcType=JdbcType.INTEGER),
        @Result(column="quatity", property="quatity", jdbcType=JdbcType.INTEGER),
        @Result(column="pic", property="pic", jdbcType=JdbcType.VARCHAR),
        @Result(column="slide", property="slide", jdbcType=JdbcType.VARCHAR),
        @Result(column="valid", property="valid", jdbcType=JdbcType.TINYINT),
        @Result(column="ctime", property="ctime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="utime", property="utime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<Commodity> selectByExample(CommodityExample example);

    @Select({
        "select",
        "id, title, brand, price, quatity, pic, slide, valid, ctime, utime",
        "from commodity",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="title", property="title", jdbcType=JdbcType.VARCHAR),
        @Result(column="brand", property="brand", jdbcType=JdbcType.VARCHAR),
        @Result(column="price", property="price", jdbcType=JdbcType.INTEGER),
        @Result(column="quatity", property="quatity", jdbcType=JdbcType.INTEGER),
        @Result(column="pic", property="pic", jdbcType=JdbcType.VARCHAR),
        @Result(column="slide", property="slide", jdbcType=JdbcType.VARCHAR),
        @Result(column="valid", property="valid", jdbcType=JdbcType.TINYINT),
        @Result(column="ctime", property="ctime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="utime", property="utime", jdbcType=JdbcType.TIMESTAMP)
    })
    Commodity selectByPrimaryKey(Integer id);

    @UpdateProvider(type=CommoditySqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") Commodity record, @Param("example") CommodityExample example);

    @UpdateProvider(type=CommoditySqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") Commodity record, @Param("example") CommodityExample example);

    @UpdateProvider(type=CommoditySqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Commodity record);

    @Update({
        "update commodity",
        "set title = #{title,jdbcType=VARCHAR},",
          "brand = #{brand,jdbcType=VARCHAR},",
          "price = #{price,jdbcType=INTEGER},",
          "quatity = #{quatity,jdbcType=INTEGER},",
          "pic = #{pic,jdbcType=VARCHAR},",
          "slide = #{slide,jdbcType=VARCHAR},",
          "valid = #{valid,jdbcType=TINYINT},",
          "ctime = #{ctime,jdbcType=TIMESTAMP},",
          "utime = #{utime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Commodity record);
}