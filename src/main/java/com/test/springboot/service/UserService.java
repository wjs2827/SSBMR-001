package com.test.springboot.service;
import java.util.List;

import com.test.springboot.dao.pojo.User;


public interface UserService {

	/**
	 * 查询用户列表
	 * @return
	 */
	List<User> queryUserList();
}
