package com.example.study.mapper;

import com.example.study.object.Unit;
import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

public interface UnitMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unit
     *
     * @mbg.generated Mon May 06 16:33:22 CST 2019
     */
    @Delete({
        "delete from unit",
        "where id = #{id,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unit
     *
     * @mbg.generated Mon May 06 16:33:22 CST 2019
     */
    @Insert({
        "insert into unit (id, unit_key, ",
        "type, name, address, ",
        "coordinate)",
        "values (#{id,jdbcType=VARCHAR}, #{unitKey,jdbcType=VARCHAR}, ",
        "#{type,jdbcType=VARCHAR}, #{name,jdbcType=VARCHAR}, #{address,jdbcType=VARCHAR}, ",
        "#{coordinate,jdbcType=VARCHAR})"
    })
    int insert(Unit record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unit
     *
     * @mbg.generated Mon May 06 16:33:22 CST 2019
     */
    @Select({
        "select",
        "id, unit_key, type, name, address, coordinate",
        "from unit",
        "where id = #{id,jdbcType=VARCHAR}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="unit_key", property="unitKey", jdbcType=JdbcType.VARCHAR),
        @Result(column="type", property="type", jdbcType=JdbcType.VARCHAR),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="address", property="address", jdbcType=JdbcType.VARCHAR),
        @Result(column="coordinate", property="coordinate", jdbcType=JdbcType.VARCHAR)
    })
    Unit selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unit
     *
     * @mbg.generated Mon May 06 16:33:22 CST 2019
     */
    @Select({
        "select",
        "id, unit_key, type, name, address, coordinate",
        "from unit"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="unit_key", property="unitKey", jdbcType=JdbcType.VARCHAR),
        @Result(column="type", property="type", jdbcType=JdbcType.VARCHAR),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="address", property="address", jdbcType=JdbcType.VARCHAR),
        @Result(column="coordinate", property="coordinate", jdbcType=JdbcType.VARCHAR)
    })
    List<Unit> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table unit
     *
     * @mbg.generated Mon May 06 16:33:22 CST 2019
     */
    @Update({
        "update unit",
        "set unit_key = #{unitKey,jdbcType=VARCHAR},",
          "type = #{type,jdbcType=VARCHAR},",
          "name = #{name,jdbcType=VARCHAR},",
          "address = #{address,jdbcType=VARCHAR},",
          "coordinate = #{coordinate,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(Unit record);

    @Select({
            "select",
            "id, unit_key, type, name, address, coordinate",
            "from unit",
            "where id = #{id,jdbcType=VARCHAR} and unit_key = #{unit_key,jdbcType=VARCHAR}"
    })
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.VARCHAR, id=true),
            @Result(column="unit_key", property="unitKey", jdbcType=JdbcType.VARCHAR),
            @Result(column="type", property="type", jdbcType=JdbcType.VARCHAR),
            @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
            @Result(column="address", property="address", jdbcType=JdbcType.VARCHAR),
            @Result(column="coordinate", property="coordinate", jdbcType=JdbcType.VARCHAR)
    })
    Unit selectByIdAndKey(@Param("id") String id, @Param("unit_key") String unit_key);
}