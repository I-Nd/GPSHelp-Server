package com.example.study.mapper;


import com.example.study.dataobject.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lenovo on 2018/12/25.
 */
//@Mapper         //声明是一个Mapper,与springbootApplication中的@MapperScan二选一写上即可
//@Repository
public interface UserMapper {

    List<User> getAllUser();

}

