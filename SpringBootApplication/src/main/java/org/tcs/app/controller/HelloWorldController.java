package org.tcs.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.tcs.app.model.Employe;
import org.tcs.app.model.Employee;
import org.tcs.app.service.EmployeeService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class HelloWorldController {

	@Autowired
	private EmployeeService employeeService;

	@RequestMapping(value = "/name", method = RequestMethod.GET)
	public String printName() {
		return "Welcome To SpringBootApplication Hello World app";
	}

	@RequestMapping(value = "/address", method = RequestMethod.GET)
	public String address() {
		return "Bangloore";

	}

	@GetMapping(value = "/EmployeeId/{id}")
	public String getEmployeeById(@PathVariable("id") String employeeId) throws JsonProcessingException {

		Employe employee = employeeService.getEmployeeData(Integer.valueOf(employeeId));
		ObjectMapper obj = new ObjectMapper();
		String emp = obj.writeValueAsString(employee);
		return emp;
	}

	@PostMapping(value = "/insertEmp")
	public ResponseEntity<String> insertEmployeeData(@RequestBody @Validated Employee employee)
			throws JsonProcessingException {
		Employe emp = new Employe();
		emp.setId(employee.getId());
		emp.setName(employee.getName());
		emp.setSal(Double.toString(employee.getSal()));
		String empRecordStatus = employeeService.insertEmpData(emp);
		return new ResponseEntity<String>(empRecordStatus, HttpStatus.OK);
	}

	@PostMapping(value = "/deleteEmp")
	public ResponseEntity<String> deleteEmployeeData(@RequestBody @Validated Employee employee)
			throws JsonProcessingException {
		Employe emp = new Employe();
		emp.setId(employee.getId());
		String empRecordStatus = employeeService.deleteEmpData(emp);
		return new ResponseEntity<String>(empRecordStatus, HttpStatus.OK);
	}

	@PostMapping(value = "/updateParticularRow")
	public String updateEmployeeById(@RequestBody @Validated Employe employee) throws JsonProcessingException {
		String str = employeeService.updateEmpData(employee);
		return str;
	}
	
	@GetMapping(value = "/retrivingEmpdata")
	public ResponseEntity<String> retriveEmpData() throws JsonProcessingException{
		ObjectMapper obj = new ObjectMapper();
		List<Employe> empList = employeeService.retriveData();
		String str=obj.writeValueAsString(empList);
		return new ResponseEntity<String>( str,HttpStatus.OK);
	}

}
