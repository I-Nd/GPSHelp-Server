package com.example.study.controller.jersey;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.study.dao.*;
import com.example.study.dataobject.*;
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
    @Autowired
    private TemporaryMapper temporaryMapper;
    @Autowired
    private ReceiverMapper receiverMapper;

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

        // 由于服务器数据库可能无该机构信息，为方便测试，插入提交的机构
        Organization organization1 = new Organization();
        organization1.setOrganization(organization);
        organization1.setLatitude(organizationLatitude);
        organization1.setLongitude(organizationLongitude);
        organization1.setTel("150****5530");
        organization1.setAddress(organization);
        organizationMapper.insert(organization1);
        //System.out.println("i:"+organization1.getOrganizationId());

        // 由于该机构是新建的测试机构，所以需要插入测试工作人员
        Worker worker = new Worker();
        worker.setOpenid("worker"+organization1.getOrganizationId());
        worker.setIsTask(new Byte("1"));
        worker.setOrganizationId(organization1.getOrganizationId());
        worker.setTel("test1505573");
        worker.setName("测试Worker"+organization1.getOrganizationId());
        worker.setJob(type);


        // 理由同上，插入测试接警员
        Receiver receiver = new Receiver();
        receiver.setTel("150****5530");
        receiver.setName("接警员");
        receiver.setOrganizationId(organization1.getOrganizationId());
        receiver.setJob(type);
        receiverMapper.insert(receiver);



        // 创建订单
        Task task = new Task();
        task.setOpenid(openid);
        task.setEventLocation(eventLocation);
        task.setEventAddress(eventLocation);
        task.setEventLatitude(eventLatitude);
        task.setEventLongitude(eventLongitude);
        task.setType(type);
        task.setOrganizationId(organization1.getOrganizationId());

        task.setReceiverId(receiver.getReceiverId().intValue());



        // 发送订单给接警员，等待接警员确认调度，若成功则向下执行，失败给出回执。

        // 模拟接警员给出的调度信息，如人数
        task.setNumberPeople(new Byte("3"));


        taskMapper.insert(task);
        // 这里默认接警员已确认调度

        worker.setTaskId(task.getTaskId().intValue());
        //worker.setImage("");
        workerMapper.insert(worker);


        // 查看是否有该用户，没有则创建
        User user = userMapper.selectByPrimaryKey(openid);
        if(user == null){
            user.setOpenid(openid);
            // 新建用户同时将状态置“1”
            user.setIsTask(new Byte("1"));
            user.setTaskId(task.getTaskId().intValue());
            userMapper.insert(user);
        } else {
            // 将已有用户状态改为1
            user.setIsTask(new Byte("1"));
            user.setTaskId(task.getTaskId().intValue());
            userMapper.updateByPrimaryKey(user);
        }

        return "请求成功";
    }


    /**
     * 用户更新定位
     * 获取救援方定位
     * 获取时间与距离
     */
    @POST
    @Path("/userUpdateLocation")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("application/json")
    public String userUpdateLocation(@FormParam("openid")String openid,
                                     @FormParam("otherOpenid")String otherOpenid,
                                     @FormParam("latitude")String latitude,@FormParam("longitude")String longitude){
        System.out.println("用户定位"+latitude+latitude);

        Temporary userTemporary = new Temporary();
        userTemporary.setOpenid(openid);
        userTemporary.setLatitude(latitude);
        userTemporary.setLongitude(longitude);
        if(temporaryMapper.selectByPrimaryKey(openid) == null){
            temporaryMapper.insert(userTemporary);
        } else {
            temporaryMapper.updateByPrimaryKey(userTemporary);
        }


        // 从表里获取救援方定位
        Temporary temporary = temporaryMapper.selectByPrimaryKey(otherOpenid);
        JSONObject jsonObject = new JSONObject();
        if(temporary == null) {

            jsonObject.put("latitude","");
            jsonObject.put("longitude","");
            jsonObject.put("time","5");
            jsonObject.put("distance","1234");
            // 此时对方未开启定位，默认填写机构
            Worker worker = workerMapper.selectByPrimaryKey(otherOpenid);
            if(worker != null){
                Organization organization = organizationMapper.selectByPrimaryKey(worker.getOrganizationId());
                if(organization != null) {
                    jsonObject.put("latitude",organization.getLatitude());
                    jsonObject.put("longitude",organization.getLongitude());
                }
            }
            return jsonObject.toJSONString();
        }

        jsonObject.put("latitude",temporary.getLatitude());
        jsonObject.put("longitude",temporary.getLongitude());
        jsonObject.put("time","5");
        jsonObject.put("distance","1234");
        return jsonObject.toJSONString();
    }

    @POST
    @Path("/workerUpdateLocation")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("application/json")
    public String workerUpdateLocation(@FormParam("openid")String openid,
                                       @FormParam("otherOpenid")String otherOpenid,
                                       @FormParam("latitude")String latitude,@FormParam("longitude")String longitude,
                                       @FormParam("toLatitude")String toLatitude,@FormParam("toLongitude")String toLongitude){

        System.out.println("工作人员定位"+latitude+latitude);
        String key = "PTVBZ-O3734-C6SUY-XFJS3-DJ3GV-Y3FTY";
        String urlHead = "https://apis.map.qq.com/ws/direction/v1/driving/";
        String from = latitude + "," + longitude;
        // 取得事故定位
        String to = toLatitude + "," + toLongitude;
        String url =  urlHead+"?from="+from+"&to="+to+"&key="+key;

        //直接返回导航信息
        JSONObject object = null;
        try {
            HttpResponse response =  HttpUtils.doGet(url,"","GET",new HashMap<String,String>(),new HashMap<String,String>());
            //System.out.println(response.toString());
            //获取response的body
            String body = EntityUtils.toString(response.getEntity());
            System.out.println("body"+body);
            object = JSON.parseObject(body);

        } catch (Exception e) {
            e.printStackTrace();
        }
        // 返回用户定位
        Temporary temporary = temporaryMapper.selectByPrimaryKey(otherOpenid);
        object.put("latitude",temporary.getLatitude());
        object.put("longitude",temporary.getLongitude());

        return object.toJSONString();

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
            // 获取自身实体类
            user = userMapper.selectByPrimaryKey(openid);
            // 根据自身所属任务id获取task任务对象
            task = taskMapper.selectByPrimaryKey(user.getTaskId().longValue());
            // 根据task_id从Worker表获取worker_id
            System.out.println("taskId"+task.getTaskId().toString());
            String workerId = workerMapper.queryByTaskId(task.getTaskId());
            System.out.println("workerId"+workerId);
            worker = workerMapper.selectByPrimaryKey(workerId);
            jsonObject.put("otherOpenid",worker.getOpenid());
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
