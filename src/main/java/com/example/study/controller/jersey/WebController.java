package com.example.study.controller.jersey;

import com.example.study.service.impl.TaskServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by tigris on 2019/5/15.
 */
@Component
@Path("/api")
public class WebController {


    @Autowired
    TaskServiceImpl taskService;

    /**
     * 添加救援人员到任务，并加入任务通信群组
     * @param taskId 任务id
     * @param rescuerList 救援人员openid，可添加多个，以逗号分隔，第一个为救援队长
     */
    @POST
    @Path("/task/{taskId}/rescuer")
    @Produces(MediaType.APPLICATION_JSON)
    public String setTaskRescuer(@PathParam("taskId") String taskId,
                                 @FormParam("rescuerList") String rescuerList){
        String[] rescuerOpenId = rescuerList.split(",");
        taskService.taskAddRescuer(taskId, rescuerOpenId[0]);
        for(String openId: rescuerOpenId){
            taskService.imGroupAddRescuer(taskId, openId);
        }
        return "";
    }

    /**
     * 救援任务完结，并删除任务通信群组
     * @param taskId 任务id
     * @return
     */
    @DELETE
    @Path("/task/{taskId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteTask(@PathParam("taskId") String taskId){
        String info = taskService.endTask(taskId);
        return info;
    }
}
