package com.example.study.dataobject.build;

import org.apache.ibatis.jdbc.SQL;

import java.util.List;

/**
 * Created by lenovo on 2019/2/15.
 */
public class TaskSqlBuild {

    /**
     * openid 求救者id
     *
     * 根据求救者id获取任务id
     */
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
