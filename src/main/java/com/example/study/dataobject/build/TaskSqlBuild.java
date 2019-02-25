package com.example.study.dataobject.build;

import org.apache.ibatis.jdbc.SQL;

import java.util.List;

/**
 * Created by lenovo on 2019/2/15.
 */
public class TaskSqlBuild {

    public String buildQueryTask(String openid){
        return new SQL(){
            {
                SELECT("task_id");
                FROM("task");
                WHERE("openid=#{openid}");

            }
        }.toString();
    }

}
