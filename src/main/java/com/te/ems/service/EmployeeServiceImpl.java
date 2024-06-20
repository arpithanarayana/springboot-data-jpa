package com.te.ems.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.te.ems.dto.EmployeeRegDTO;
import com.te.ems.dto.TechnologyDTO;
import com.te.ems.entity.Address;
import com.te.ems.entity.Technology;
import com.te.ems.exception.EmployeeNotFoundException;
import com.te.ems.repository.TechnologyRepository;
import org.springframework.stereotype.Service;

import com.te.ems.dto.AddressDTO;
import com.te.ems.dto.EmployeeBasicDTO;
import com.te.ems.entity.Employee;
import com.te.ems.repository.AddressRepository;
import com.te.ems.repository.EmployeeRepository;
import com.te.ems.util.EmployeeUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService {
 	private final EmployeeRepository employeeRepository;
	private final TechnologyRepository technologyRepository;
	private final AddressRepository addressRepository;

	@Override
	public EmployeeBasicDTO getEmployee(Integer employeeId) {	
		Employee employee =  employeeRepository.findById(employeeId)
				.orElseThrow(() -> new EmployeeNotFoundException("Employee data not found on given employee Id"));

		return EmployeeUtils.convertEntityToDTO_(employee);
	}

	@Override
	public Integer saveEmployee(EmployeeRegDTO employeeRegDTO) {
		Employee employee = EmployeeUtils.convertDTOToEntity(employeeRegDTO);
		employeeRegDTO.technologies().stream().forEach(technologyDTO -> {
			Optional<Technology> optionalTechnology = technologyRepository.findById(technologyDTO.getTechnologyName());
			if(optionalTechnology.isPresent()){
				Technology t = optionalTechnology.get();
				employee.getTechnologies().add(t);
				t.getEmployees().add(employee);
			} else {
				Technology t = technologyRepository.save(Technology.builder()
						.technologyName(technologyDTO.getTechnologyName()).build());
				employee.getTechnologies().add(t);
				t.getEmployees().add(employee);
			}
			employeeRepository.save(employee);
		});

		return employee.getEmployeeId();
	}

	@Override
	public boolean updateEmployee(Integer employeeId, EmployeeRegDTO employeeRegDTO) {
		Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new EmployeeNotFoundException("employee is not found"));
		//employeeRegDTO.addresses().clear();
		employee.setAddresses(employeeRegDTO.addresses().stream().map(e -> {
			return Address.builder().addressCity(e.getAddressCity())
					.addressState(e.getAddressState())
					.addressType(e.getAddressType())
					.build();
		}).collect(Collectors.toList()));
		
	    employeeRegDTO.technologies().clear();
	   
		employeeRegDTO.technologies().stream().forEach(technologyDTO -> {
			Optional<Technology> optionalTechnology = technologyRepository.findById(technologyDTO.getTechnologyName());
			if(optionalTechnology.isPresent()){
				Technology t = optionalTechnology.get();
				employee.getTechnologies().add(t);
				t.getEmployees().add(employee);
			} else {
				Technology t = technologyRepository.save(Technology.builder().technologyName(technologyDTO.getTechnologyName()).build());
				employee.getTechnologies().add(t);
				t.getEmployees().add(employee);
			}
		});
		EmployeeUtils.updateFields(employee,employeeRegDTO);
		employeeRepository.save(employee);
		return true;
	}

	@Override
	public boolean deleteEmployee(Integer employeeId) {
		Employee employee1 = employeeRepository.findById(employeeId).orElseThrow(() -> new EmployeeNotFoundException("employee not found exception"));
		employee1.setTechnologies(null);
		employeeRepository.save(employee1);

        if(employee1.getEmployeeId()!=null)
		{
			employeeRepository.deleteById(employeeId);
			return true;
		}
		return false;
	}
	
	@Override
	public List<EmployeeBasicDTO> getAllEmployees(String addressCity) {
		List<Employee> employee = employeeRepository.findByEmployeeCity(addressCity);
		if (!employee.isEmpty()) {
	        return employee.stream().map(e -> EmployeeUtils.convertEntityToDTO_(e)).toList();
	    } else {
	    	throw new EmployeeNotFoundException("Employee not found based on the technology name");
	    }
	}

	@Override
	public List<EmployeeBasicDTO> getAllEmployeesTechnology(String technologyName) {
		List<Employee> technology = employeeRepository.findByEmployeeTechnology(technologyName);
		if (!technology.isEmpty()) {
	        return technology.stream().map(e -> EmployeeUtils.convertEntityToDTO_(e)).toList();
	    } else {
	        throw new EmployeeNotFoundException("Employee not found based on the technology name");
	    }
	}

	@Override
	public List<EmployeeRegDTO> getAllEmployeesIFSC(String bankIFSC) {
		List<Employee> employeeIFSC = employeeRepository.findByEmployeeIFSC(bankIFSC);
		if (!employeeIFSC.isEmpty()) {
	        return employeeIFSC.stream().map(e -> EmployeeUtils.convertEntityToDTOs(e)).toList();
	    } else {
	    	throw new EmployeeNotFoundException("Employee not found based on the IFSC");
	    }
	}
	
	//employeeRegDTO.addresses().forEach(addressDTO -> {
//        Optional<Address> optional = addressRepository.findById(addressDTO.getAddressId());
//        if (optional.isPresent()) {
//            Address existingAddress = optional.get();
//
//            // Check if any changes before updating
//            if (!existingAddress.getAddressCity().equals(addressDTO.getAddressCity())
//                    || !existingAddress.getAddressState().equals(addressDTO.getAddressState())
//                    || !existingAddress.getAddressType().equals(addressDTO.getAddressType())) {
//                existingAddress.setAddressCity(addressDTO.getAddressCity());
//                existingAddress.setAddressState(addressDTO.getAddressState());
//                existingAddress.setAddressType(addressDTO.getAddressType());
//            }
//        }
//    });
}
