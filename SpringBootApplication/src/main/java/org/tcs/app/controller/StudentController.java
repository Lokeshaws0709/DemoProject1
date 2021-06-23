package org.tcs.app.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.tcs.app.model.Student;
import org.tcs.app.request.StudentListReq;
import org.tcs.app.request.StudentRequest;
import org.tcs.app.service.StudentService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class StudentController {
	@Autowired
	private StudentService studentService;

	@PostMapping(value = "/insertStudentData")
	public ResponseEntity<String> inserStudentData(@RequestBody @Validated StudentRequest studentrequest)
			throws JsonProcessingException {

		Student std = new Student();
		std.setId(studentrequest.getId());
		std.setName(studentrequest.getName());
		std.setBranch(studentrequest.getBranch());
		std.setCity(studentrequest.getCity());
		std.setPhno(studentrequest.getPhno());
		String str = studentService.insertStdData(std);

		return new ResponseEntity<String>(str, HttpStatus.OK);
	}

	@GetMapping(value = "/StudentData/{id}")
	public String getStudentById(@PathVariable("id") String studentId) throws JsonProcessingException {

		Student std = studentService.getStudentData(Integer.valueOf(studentId));
		ObjectMapper obj = new ObjectMapper();
		String str = obj.writeValueAsString(std);

		return str;

	}

	@PostMapping(value = "/updateStdRow")
	public ResponseEntity<String> updateStudentById(@RequestBody @Validated StudentRequest req)
			throws JsonProcessingException {
		Student std = new Student();
		std.setId(req.getId());
		std.setName(req.getName());
		std.setBranch(req.getBranch());
		std.setCity(req.getCity());
		std.setPhno(req.getPhno());
		String str = studentService.updateStdDynamicData(std);
		return new ResponseEntity<String>(str, HttpStatus.OK);
	}

	@GetMapping(value = "/retriveStddata")
	public ResponseEntity<String> retriveStdData() throws JsonProcessingException {
		ObjectMapper obj = new ObjectMapper();
		List<Student> stdList = studentService.retriveData();
		String str = obj.writeValueAsString(stdList);
		return new ResponseEntity<String>(str, HttpStatus.OK);
	}
	
	@PostMapping(value = "/insertlistofdata")
	public ResponseEntity<String> insertListOfStdData(@RequestBody @Validated StudentListReq studentListReq) throws JsonProcessingException {
	List<StudentRequest> studentReq = studentListReq.getStdlist();
	List<Student> studentList = new ArrayList<>();
	for(StudentRequest stdreq : studentReq) {
		Student std = new Student();
		std.setId(stdreq.getId());
		std.setName(stdreq.getName());
		std.setBranch(stdreq.getBranch());
		std.setCity(stdreq.getCity());
		std.setPhno(stdreq.getPhno());
		studentList.add(std);
	}
		ObjectMapper obj = new ObjectMapper();
		List<String> stdList = studentService.insertStdListData(studentList);
		String str = obj.writeValueAsString(stdList);
		return new ResponseEntity<String>(str, HttpStatus.OK);
	}
	
}
