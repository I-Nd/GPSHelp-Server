package com.example.study.dao;

import com.example.study.dataobject.Worker;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;

public interface WorkerMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table worker
     *
     * @mbg.generated Fri Feb 15 00:03:24 CST 2019
     */
    @Delete({
        "delete from worker",
        "where openid = #{openid,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(String openid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table worker
     *
     * @mbg.generated Fri Feb 15 00:03:24 CST 2019
     */
    @Insert({
        "insert into worker (openid, tel, ",
        "is_task, job, name, ",
        "task_id, organization_id, ",
        "image)",
        "values (#{openid,jdbcType=VARCHAR}, #{tel,jdbcType=VARCHAR}, ",
        "#{isTask,jdbcType=TINYINT}, #{job,jdbcType=VARCHAR}, #{name,jdbcType=VARCHAR}, ",
        "#{taskId,jdbcType=INTEGER}, #{organizationId,jdbcType=BIGINT}, ",
        "#{image,jdbcType=VARCHAR})"
    })
    int insert(Worker record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table worker
     *
     * @mbg.generated Fri Feb 15 00:03:24 CST 2019
     */
    @Select({
        "select",
        "openid, tel, is_task, job, name, task_id, organization_id, image",
        "from worker",
        "where openid = #{openid,jdbcType=VARCHAR}"
    })
    @Results({
        @Result(column="openid", property="openid", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="tel", property="tel", jdbcType=JdbcType.VARCHAR),
        @Result(column="is_task", property="isTask", jdbcType=JdbcType.TINYINT),
        @Result(column="job", property="job", jdbcType=JdbcType.VARCHAR),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="task_id", property="taskId", jdbcType=JdbcType.INTEGER),
        @Result(column="organization_id", property="organizationId", jdbcType=JdbcType.BIGINT),
        @Result(column="image", property="image", jdbcType=JdbcType.VARCHAR)
    })
    Worker selectByPrimaryKey(String openid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table worker
     *
     * @mbg.generated Fri Feb 15 00:03:24 CST 2019
     */
    @Select({
        "select",
        "openid, tel, is_task, job, name, task_id, organization_id, image",
        "from worker"
    })
    @Results({
        @Result(column="openid", property="openid", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="tel", property="tel", jdbcType=JdbcType.VARCHAR),
        @Result(column="is_task", property="isTask", jdbcType=JdbcType.TINYINT),
        @Result(column="job", property="job", jdbcType=JdbcType.VARCHAR),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="task_id", property="taskId", jdbcType=JdbcType.INTEGER),
        @Result(column="organization_id", property="organizationId", jdbcType=JdbcType.BIGINT),
        @Result(column="image", property="image", jdbcType=JdbcType.VARCHAR)
    })
    List<Worker> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table worker
     *
     * @mbg.generated Fri Feb 15 00:03:24 CST 2019
     */
    @Update({
        "update worker",
        "set tel = #{tel,jdbcType=VARCHAR},",
          "is_task = #{isTask,jdbcType=TINYINT},",
          "job = #{job,jdbcType=VARCHAR},",
          "name = #{name,jdbcType=VARCHAR},",
          "task_id = #{taskId,jdbcType=INTEGER},",
          "organization_id = #{organizationId,jdbcType=BIGINT},",
          "image = #{image,jdbcType=VARCHAR}",
        "where openid = #{openid,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(Worker record);
}