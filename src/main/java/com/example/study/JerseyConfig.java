package com.example.study;

import com.example.study.controller.jersey.UserResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import javax.ws.rs.ApplicationPath;

/**
 * Created by lenovo on 2019/2/7.
 */
@Component
@ApplicationPath("/jersey")
public class JerseyConfig extends ResourceConfig{

    public JerseyConfig(){
        packages("com.example.study.controller.jersey");
        //register(UserResource.class);
    }
}
