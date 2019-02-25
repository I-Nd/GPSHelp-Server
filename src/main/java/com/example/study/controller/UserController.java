package com.example.study.controller;


import com.example.study.dataobject.User;
import com.example.study.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by lenovo on 2018/12/25.
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {
    private final static Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;


    @RequestMapping("/getAllUser")
    @ResponseBody
    public List<User> getAllUser() {
        List<User> users =  userService.getAllUser();
        logger.debug("getAllUser");
        return users;

    }
    @RequestMapping("/list")
    public String  listUser(Model model) {
        List<User> userList = getAllUser();
        model.addAttribute("users", userList);
        return "user/list";
    }
    @RequestMapping("/login")
    public String login(){
        return "login";
    }

}
