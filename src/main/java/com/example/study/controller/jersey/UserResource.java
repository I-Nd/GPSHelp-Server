package com.example.study.controller.jersey;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.study.dao.OrganizationMapper;
import com.example.study.dao.TaskMapper;
import com.example.study.dao.UserMapper;
import com.example.study.dao.WorkerMapper;
import com.example.study.dataobject.Organization;
import com.example.study.dataobject.Task;
import com.example.study.dataobject.User;
import com.example.study.dataobject.Worker;
import com.example.study.service.UserService;
import com.example.study.util.HttpUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.List;

/**
 * Created by lenovo on 2019/2/7.
 */
@Component
@Path("/users")
public class UserResource {

    @Autowired
    private UserService userService;
    @Autowired
    private WorkerMapper workerMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private OrganizationMapper organizationMapper;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public User get(){
        List<User> users = userService.getAllUser();
        return users.get(0);
    }
    @GET
    @Path("/getInitMsg")
    @Produces(MediaType.APPLICATION_JSON)
    public String getInitMsg(@QueryParam("code") String code){
        String appid = "wxa466fa6fe3836361";
        String secret = "6b2cd5552ac166ea396a6e14ad4fda60";
        String js_code = code;
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appid + "&secret=" + secret + "&js_code=" + js_code + "&grant_type=authorization_code";
        System.out.println(url);

        String openId = "oAxVW4yKShgrBl_SZXyZTWRgvNYk";
        String session_key = "LXeECSi1Khj1sR47Phgztw==";


        try {
            HttpResponse response =  HttpUtils.doGet(url,"","GET",new HashMap<String,String>(),new HashMap<String,String>());
            //System.out.println(response.toString());
            //获取response的body
            String body = EntityUtils.toString(response.getEntity());
            System.out.println("body"+body);
            JSONObject object = JSON.parseObject(body);
            openId = object.getString("openid");
            session_key = object.getString("session_key");
            System.out.print("openid"+object.getString("openid"));

        } catch (Exception e) {
            e.printStackTrace();
        }

        Boolean isWorker;
        Boolean isTask ;
        Worker worker = workerMapper.selectByPrimaryKey(openId);
        System.out.println("worker"+worker);
        if(worker != null){
            isWorker = true;
            if(worker.getIsTask()==0){
                isTask = false;
            } else {
                isTask = true;
            }

        } else {
            isWorker = false;
            User user = userMapper.selectByPrimaryKey(openId);
            System.out.println("user"+user);
            if(user!=null){
                if(user.getIsTask() == 0){
                    isTask = false;
                }else {
                    isTask = true;
                }
            }else {
                isTask = false;
            }
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("openid",openId);
        jsonObject.put("session_key",session_key);
        jsonObject.put("isWorker",isWorker);
        jsonObject.put("isTask",isTask);
        return JSONObject.toJSONString(jsonObject);
    }
    @POST
    @Path("/submit")
    @Consumes("application/x-www-form-urlencoded")
    public String submit(@FormParam("openid")String openid,@FormParam("organization_id")String organization_id,
                         @FormParam("organization")String organization,
                         @FormParam("organizationLatitude")String organizationLatitude,
                         @FormParam("organizationLongitude")String organizationLongitude,
                         @FormParam("eventLocation")String eventLocation,
                         @FormParam("eventLatitude")String eventLatitude,
                         @FormParam("eventLongitude")String eventLongitude,
                         @FormParam("type")String type){
        Organization organization1 = new Organization();
        organization1.setOrganization(organization);
        organization1.setLatitude(organizationLatitude);
        organization1.setLongitude(organizationLongitude);
        organizationMapper.insert(organization1);
        System.out.println("i:"+organization1.getOrganizationId());
        Task task = new Task();
        task.setOpenid(openid);
        task.setEventLocation(eventLocation);
        task.setEventLatitude(eventLatitude);
        task.setEventLongitude(eventLongitude);
        task.setType(type);
        task.setOrganizationId(organization1.getOrganizationId());
        int j = taskMapper.query(openid).get(taskMapper.query(openid).size()-1);
        System.out.println("j:"+j);
        User user = userMapper.selectByPrimaryKey(openid);
        if(user == null){
            user.setOpenid(openid);
            user.setIsTask(new Byte("1"));
            user.setTaskId(j);
            userMapper.insert(user);
        } else {
            user.setIsTask(new Byte("1"));
            user.setTaskId(j);
            userMapper.updateByPrimaryKey(user);
        }


        System.out.println("/submit"+openid);
        return "";
    }

    @POST
    @Path("/userUpdateLocation")
    @Consumes("application/x-www-form-urlencoded")
    public String userUpdateLocation(@FormParam("openid")String openid,
                                     @FormParam("latitude")String latitude,@FormParam("longitude")String eventLongitude){
        System.out.println("定位"+latitude+latitude);
        return "ok";
    }
    @GET
    @Path("/updateWorkerLocation")
    public String updateWorkerLocation(){

        return "";
    }

    @GET
    @Path("/getTask")
    @Produces(MediaType.APPLICATION_JSON)
    public String getTask(@QueryParam("isWorker") boolean isWorker,@QueryParam("openid") String openid){
        User user = null;
        Worker worker = null;
        Task task = null;
        JSONObject jsonObject = new JSONObject();
        if(isWorker){
            worker = workerMapper.selectByPrimaryKey(openid);
            // 通过worker的task_id获取任务task，任务类型，事故地点，事故坐标
            task = taskMapper.selectByPrimaryKey(worker.getTaskId().longValue());

            // 获取用户姓名，电话
            user = userMapper.selectByPrimaryKey(task.getOpenid());
            jsonObject.put("name",user.getName());
            jsonObject.put("tel",user.getTel());

        } else {
            //获取救援方姓名，救援方电话，救援方人数,任务类型，事故地点，事故坐标
            user = userMapper.selectByPrimaryKey(openid);
            task = taskMapper.selectByPrimaryKey(user.getTaskId().longValue());
            worker = workerMapper.selectByPrimaryKey("worker_1");
            jsonObject.put("name",worker.getName());
            jsonObject.put("tel",worker.getTel());
            jsonObject.put("image",worker.getImage());
            jsonObject.put("number_people",task.getNumberPeople());


        }
        jsonObject.put("type",task.getType());
        jsonObject.put("event_location",task.getEventLocation());
        jsonObject.put("event_longitude",task.getEventLongitude());
        jsonObject.put("event_latitude",task.getEventLatitude());
        // 通过task_id的organization_id获取救援方驻点信息，救援方名称，救援方坐标
        Organization organization = organizationMapper.selectByPrimaryKey(task.getOrganizationId());
        jsonObject.put("organization",organization.getOrganization());
        jsonObject.put("organization_longitude",organization.getLongitude());
        jsonObject.put("organization_latitude",organization.getLatitude());
        return jsonObject.toJSONString();
    }

}
