package com.test.springboot.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.test.springboot.dao.pojo.Student;

@Mapper
public interface StudentMapper {

	public Student getStudentById(Integer id);

	public List<Student> selectStudentList();

	public Integer insetStudent(Student student);

	public Integer updateStudent(Student student);

	public Integer deleteStudent(Integer id);
}
