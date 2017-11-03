package com.test.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.test.springboot.dao.pojo.Student;
import com.test.springboot.service.StudentService;

@Controller
public class StudentController {

	@Autowired
	private StudentService studentService;
	
	@RequestMapping("index")
	public String getData(Model model){
		List<Student> list=studentService.selectStudentList();
		model.addAttribute("list", list);
		return "student/index";
	}
	
	@RequestMapping("student")
	@ResponseBody
	public Student getStudent(Integer id){
		 return studentService.getStudentById(id);
	}
	
	@RequestMapping("toAdd")
	public String toAdd(Integer id,Model model){
		if(id!=null&&id!=0){
			Student student = studentService.getStudentById(id);
			model.addAttribute("student", student);
		}
		return "student/add";
	}
	
	@RequestMapping("addUpdateStudent")
	public String addUpdateStudent(Student student){
		if(student.getId()!=null&&student.getId()!=0){
			studentService.updateStudent(student);
		}else{
			studentService.insetStudent(student);
		}
		return "redirect:index";
	}
	
	@RequestMapping("toDelete")
	public String toDelete(Integer id){
		if(id!=null&&id!=0){
			studentService.deleteStudent(id);
		}
		return "redirect:index";
	}
	
	
	
}
