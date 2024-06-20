package com.te.ems.service;

import com.te.ems.dto.EmployeeBasicDTO;
import com.te.ems.dto.EmployeeRegDTO;
import com.te.ems.entity.Employee;

import java.util.List;

public interface EmployeeService {

	EmployeeBasicDTO getEmployee(Integer employeeId);

	List<EmployeeBasicDTO> getAllEmployees(String addressCity);

	Integer saveEmployee(EmployeeRegDTO employeeRegDTO);

    boolean updateEmployee(Integer employeeId, EmployeeRegDTO employeeRegDTO);

    boolean deleteEmployee(Integer employeeId);

	List<EmployeeBasicDTO> getAllEmployeesTechnology(String technologyName);

	List<EmployeeRegDTO> getAllEmployeesIFSC(String bankIFSC);
}
