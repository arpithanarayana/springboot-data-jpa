package com.te.ems.controller;

import java.time.LocalDateTime;
import java.util.List;

import com.te.ems.dto.*;
import com.te.ems.entity.AddressType;
import com.te.ems.entity.BankAccount;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.te.ems.response.SuccessResponse;
import com.te.ems.service.EmployeeService;

@RequestMapping(path = "api/employees")
@RestController
public record EmployeeController(EmployeeService employeeService) {

	@GetMapping(path = "dummy")
	public EmployeeRegDTO dummy(){
		return  EmployeeRegDTO
				.builder()
				.employeeName("abc")
				.employeeEmailId("abc123")
				.bankAccountDTO(BankAccountDTO
						.builder().bankAccountNumber("123456789").bankIFSC("AFC123").build())
				.addresses(List.of(AddressDTO.builder().addressState("state").addressCity("city").addressType(AddressType.PERMANENT).build()))
				.technologies(List.of(TechnologyDTO.builder().technologyName("java").build()))
				.build();
	}

	@GetMapping(path = "employee")
	public ResponseEntity<SuccessResponse> getEmployee(@RequestParam(name = "eid") Integer employeeId) {
		return ResponseEntity.<SuccessResponse>ofNullable(
				SuccessResponse.builder()
				.message("Employee data provided").status(HttpStatus.OK)
				.data(employeeService.getEmployee(employeeId))
				.timestamp(LocalDateTime.now())
				.build()
				);
	}
	
	@GetMapping(path = "")
	public ResponseEntity<SuccessResponse> getAllEmployeesCity(@RequestParam(name = "city") String addressCity) {
		return ResponseEntity.<SuccessResponse>ofNullable(
				SuccessResponse.builder()
						.message("Employee data provided").status(HttpStatus.OK)
						.data(employeeService.getAllEmployees(addressCity))
						.timestamp(LocalDateTime.now())
						.build()
		);
	}
	
	@GetMapping(path = "technologyName")
	public ResponseEntity<SuccessResponse> getAllEmployeesTechnology(@RequestParam(name = "name") String technologyName) {
		return ResponseEntity.<SuccessResponse>ofNullable(
				SuccessResponse.builder()
						.message("Employee data provided").status(HttpStatus.OK)
						.data(employeeService.getAllEmployeesTechnology(technologyName))
						.timestamp(LocalDateTime.now())
						.build()
		);
	}
	
	@GetMapping(path = "bankIFSC")
	public ResponseEntity<SuccessResponse> getAllEmployeesIFSC(@RequestParam(name = "IFSC") String bankIFSC) {
		return ResponseEntity.<SuccessResponse>ofNullable(
				SuccessResponse.builder()
						.message("Employee data provided").status(HttpStatus.OK)
						.data(employeeService.getAllEmployeesIFSC(bankIFSC))
						.timestamp(LocalDateTime.now())
						.build()
		);
	}


	@PostMapping(path = "employee")
	public ResponseEntity<SuccessResponse> saveEmployees(@RequestBody EmployeeRegDTO employeeRegDTO) {
		return ResponseEntity.<SuccessResponse>ofNullable(
				SuccessResponse.builder()
						.message("Employee data provided")
						.status(HttpStatus.CREATED)
						.data(employeeService.saveEmployee(employeeRegDTO))
						.timestamp(LocalDateTime.now())
						.build()
		);
	}

	@PutMapping(path = "employee")
	public ResponseEntity<SuccessResponse> updateEmployees(@RequestParam(name ="eid") Integer employeeId,@RequestBody EmployeeRegDTO employeeRegDTO) {
		return ResponseEntity.<SuccessResponse>ofNullable(
				SuccessResponse.builder()
						.message("Employee data update sucessfully")
						.status(HttpStatus.OK)
						.data(employeeService.updateEmployee(employeeId,employeeRegDTO))
						.timestamp(LocalDateTime.now())
						.build()
		);
	}

	@DeleteMapping(path = "")
	public ResponseEntity<SuccessResponse> deleteEmployees(@RequestParam(name ="eid") Integer employeeId) {
		return ResponseEntity.<SuccessResponse>ofNullable(
				SuccessResponse.builder()
						.message("Employee data deleted sucessfully based on id")
						.status(HttpStatus.OK)
						.data(employeeService.deleteEmployee(employeeId))
						.timestamp(LocalDateTime.now())
						.build()
		);
	}

}
