package com.test.springboot.service;

import java.util.List;

import com.test.springboot.dao.pojo.Student;

public interface StudentService {
	
	public Student getStudentById(Integer id);

	public List<Student> selectStudentList();

	public Integer insetStudent(Student student);

	public Integer updateStudent(Student student);

	public Integer deleteStudent(Integer id);
}
