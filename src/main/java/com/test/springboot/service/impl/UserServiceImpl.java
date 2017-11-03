package com.test.springboot.service.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.test.springboot.dao.UserMapper;
import com.test.springboot.dao.pojo.User;
import com.test.springboot.service.UserService;
import com.test.springboot.util.RedisUtil;

@Transactional
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private RedisUtil redisUtil;
	
	public List<User> queryUserList() {
		//在此测试redis的事务
		redisUtil.set("bbb", 666);
		System.out.println(redisUtil.get("bbb"));
		return userMapper.queryList();
	}

}
