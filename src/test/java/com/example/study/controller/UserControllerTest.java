package com.example.study.controller;

import com.example.study.DemoApplicationTests;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by lenovo on 2019/2/3.
 */

public class UserControllerTest extends DemoApplicationTests {

    @Autowired
    private UserController userController;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testListUser() throws Exception {

    }

    @Test
    public void testGetAllUser() throws Exception {

        Assert.assertNotNull("为空！",userController.getAllUser());
        //System.out.print("==========="+userController.getAllUser().get(0).getName());

    }


}