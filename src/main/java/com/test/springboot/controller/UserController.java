package com.test.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.test.springboot.dao.pojo.User;
import com.test.springboot.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/queryUser")
	@ResponseBody
	public void queryList(){
		List<User> list=userService.queryUserList();
		for (User user : list) {
			System.out.println("username:"+user.getUsername()+"\n");
			System.out.println("password:"+user.getPassword()+"\n");
		}
	}

}
