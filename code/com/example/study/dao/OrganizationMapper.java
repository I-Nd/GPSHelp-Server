package com.example.study.dao;

import com.example.study.dataobject.Organization;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;

public interface OrganizationMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table organization
     *
     * @mbg.generated Fri Feb 15 00:02:11 CST 2019
     */
    @Delete({
        "delete from organization",
        "where organization_id = #{organizationId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long organizationId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table organization
     *
     * @mbg.generated Fri Feb 15 00:02:11 CST 2019
     */
    @Insert({
        "insert into organization (organization_id, organization, ",
        "longitude, latitude, ",
        "address, tel)",
        "values (#{organizationId,jdbcType=BIGINT}, #{organization,jdbcType=VARCHAR}, ",
        "#{longitude,jdbcType=VARCHAR}, #{latitude,jdbcType=VARCHAR}, ",
        "#{address,jdbcType=VARCHAR}, #{tel,jdbcType=VARCHAR})"
    })
    int insert(Organization record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table organization
     *
     * @mbg.generated Fri Feb 15 00:02:11 CST 2019
     */
    @Select({
        "select",
        "organization_id, organization, longitude, latitude, address, tel",
        "from organization",
        "where organization_id = #{organizationId,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="organization_id", property="organizationId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="organization", property="organization", jdbcType=JdbcType.VARCHAR),
        @Result(column="longitude", property="longitude", jdbcType=JdbcType.VARCHAR),
        @Result(column="latitude", property="latitude", jdbcType=JdbcType.VARCHAR),
        @Result(column="address", property="address", jdbcType=JdbcType.VARCHAR),
        @Result(column="tel", property="tel", jdbcType=JdbcType.VARCHAR)
    })
    Organization selectByPrimaryKey(Long organizationId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table organization
     *
     * @mbg.generated Fri Feb 15 00:02:11 CST 2019
     */
    @Select({
        "select",
        "organization_id, organization, longitude, latitude, address, tel",
        "from organization"
    })
    @Results({
        @Result(column="organization_id", property="organizationId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="organization", property="organization", jdbcType=JdbcType.VARCHAR),
        @Result(column="longitude", property="longitude", jdbcType=JdbcType.VARCHAR),
        @Result(column="latitude", property="latitude", jdbcType=JdbcType.VARCHAR),
        @Result(column="address", property="address", jdbcType=JdbcType.VARCHAR),
        @Result(column="tel", property="tel", jdbcType=JdbcType.VARCHAR)
    })
    List<Organization> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table organization
     *
     * @mbg.generated Fri Feb 15 00:02:11 CST 2019
     */
    @Update({
        "update organization",
        "set organization = #{organization,jdbcType=VARCHAR},",
          "longitude = #{longitude,jdbcType=VARCHAR},",
          "latitude = #{latitude,jdbcType=VARCHAR},",
          "address = #{address,jdbcType=VARCHAR},",
          "tel = #{tel,jdbcType=VARCHAR}",
        "where organization_id = #{organizationId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(Organization record);
}