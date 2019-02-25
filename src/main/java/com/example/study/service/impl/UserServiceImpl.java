package com.example.study.service.impl;

import com.example.study.dao.UserMapper;
import com.example.study.dataobject.User;
import com.example.study.service.UserService;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lenovo on 2018/12/25.
 */
@Service(value = "userService")
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;


    @Override
    public List<User> getAllUser() {
        return userMapper.selectAll();
    }
}

