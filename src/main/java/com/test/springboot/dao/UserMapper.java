package com.test.springboot.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.test.springboot.dao.pojo.User;

@Mapper
public interface UserMapper {
    
    List<User> queryList();
}