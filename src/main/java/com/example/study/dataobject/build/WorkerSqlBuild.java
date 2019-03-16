package com.example.study.dataobject.build;

import org.apache.ibatis.jdbc.SQL;

/**
 * Created by lenovo on 2019/3/16.
 */
public class WorkerSqlBuild {

    public String buildQueryWorker(Long task_id){
        return new SQL(){
            {
                SELECT("openid");
                FROM("worker");
                WHERE("task_id=#{task_id}");

            }
        }.toString();
    }
}
