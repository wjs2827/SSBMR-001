package com.test.springboot.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.test.springboot.dao.StudentMapper;
import com.test.springboot.dao.pojo.Student;
import com.test.springboot.service.StudentService;
import com.test.springboot.util.RedisUtil;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentMapper studentMapper;
	
	@Autowired
	private RedisUtil redisUtil;
	
	@Override
	public Student getStudentById(Integer id) {
		Student student = studentMapper.getStudentById(id);
		return student;
	}

	@Override
	@Transactional
	public List<Student> selectStudentList() {
		//在此测试redis的事务
		redisUtil.set("bbb", 666);
		return studentMapper.selectStudentList();
	}

	@Override
	@Transactional
	public Integer insetStudent(Student student) {
		Integer integer = studentMapper.insetStudent(student);
		return integer;
	}

	@Override
	@Transactional
	public Integer updateStudent(Student student) {
		Integer integer = studentMapper.updateStudent(student);
		return integer;
	}

	@Override
	public Integer deleteStudent(Integer id) {
		return studentMapper.deleteStudent(id);
	}

}
