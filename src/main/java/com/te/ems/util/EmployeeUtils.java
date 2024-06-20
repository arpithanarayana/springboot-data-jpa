package com.te.ems.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.te.ems.dto.AddressDTO;
import com.te.ems.dto.BankAccountDTO;
import com.te.ems.dto.EmployeeBasicDTO;
import com.te.ems.dto.EmployeeRegDTO;
import com.te.ems.dto.TechnologyDTO;
import com.te.ems.entity.Address;
import com.te.ems.entity.BankAccount;
import com.te.ems.entity.Employee;
import com.te.ems.entity.Technology;
import com.te.ems.exception.AddressTypeNotFoundException;

public class EmployeeUtils {
    public static EmployeeBasicDTO convertEntityToDTO(Employee employee) {
        EmployeeBasicDTO employeeBasicDTO = EmployeeBasicDTO.builder()
                .employeeId(employee.getEmployeeId())
                .employeeEmailId(employee.getEmployeeEmailId())
                .employeeName(employee.getEmployeeName())
                .build();

        List<AddressDTO> addressDTOs = new ArrayList<>();

        for (Address address : employee.getAddresses()) {
            addressDTOs.add(AddressDTO.builder()
                    .addressCity(address.getAddressCity())
                    .addressState(address.getAddressState())
                    .addressType(address.getAddressType())
                    .build());
        }
        employeeBasicDTO.setAddresses(addressDTOs);

        List<TechnologyDTO> technologyDTOs = new ArrayList<>();
        for (Technology technology : employee.getTechnologies()) {
            technologyDTOs.add(TechnologyDTO.builder()
                    .technologyName(technology.getTechnologyName())
                    .build());
        }
        employeeBasicDTO.setTechnologies(technologyDTOs);
        return employeeBasicDTO;
    }

    public static EmployeeBasicDTO convertEntityToDTO_(Employee employee) {
        return EmployeeBasicDTO.builder()
                .employeeId(employee.getEmployeeId())
                .employeeEmailId(employee.getEmployeeEmailId())
                .employeeName(employee.getEmployeeName())
                .addresses(employee.getAddresses().stream()
                        .map(a -> AddressDTO.builder()
                                .addressCity(a.getAddressCity())
                                .addressState(a.getAddressState())
                                .addressType(a.getAddressType())
                                .build()).collect(Collectors.toList()))
                .technologies(employee.getTechnologies().stream()
                        .map(t -> TechnologyDTO.builder()
                                .technologyName(t.getTechnologyName())
                                .build()).toList())
                .build();
    }

    public static Employee convertDTOToEntity(EmployeeRegDTO employeeRegDTO) {

        Employee employee = Employee.builder()
                .employeeName(employeeRegDTO.employeeName())
                .employeeEmailId(employeeRegDTO.employeeEmailId())
                .technologies(new ArrayList<>())
                .build();

        BankAccount bankAccount = BankAccount.builder()
                .bankAccountNumber(employeeRegDTO.bankAccountDTO().bankAccountNumber())
                .bankIFSC(employeeRegDTO.bankAccountDTO().bankIFSC())
                .employee(employee)
                .build();

        List<Address> addresses = employeeRegDTO.addresses().stream()
                .map(a -> {
                    if (Objects.isNull(a.getAddressType()))
                        throw new AddressTypeNotFoundException("Address type for this address is not found");
                    return Address.builder()
                            .addressCity(a.getAddressCity())
                            .addressState(a.getAddressState())
                            .addressType(a.getAddressType())
                            .employee(employee)
                            .build();
                }).collect(Collectors.toList());

//     List<Technology> technologies = employeeRegDTO.technologies().stream()
//             .map(t -> Technology.builder()
//                         .technologyName(t.getTechnologyName())
//                         .employees(new ArrayList<>())
//                         .build()).toList();
//    technologies.forEach(technology -> technology.getEmployees().add(employee));


        employee.setBankAccount(bankAccount);
        employee.setAddresses(addresses);
        addresses.forEach(address -> address.setEmployee(employee));
        return employee;
    }

    public static void updateFields(Employee employee,EmployeeRegDTO employeeRegDTO){
        employee.setEmployeeEmailId(employeeRegDTO.employeeEmailId());
        employee.setEmployeeName(employeeRegDTO.employeeName());
        employee.setAddresses(employeeRegDTO.addresses().stream().map(e ->
                Address.builder().addressCity(e.getAddressCity())
                        .addressState(e.getAddressState())
                        .addressType(e.getAddressType()).build()
        ).collect(Collectors.toList()));
        employee.setBankAccount(BankAccount.builder().bankIFSC(employeeRegDTO.bankAccountDTO().bankIFSC())
                .bankAccountNumber(employeeRegDTO.bankAccountDTO().bankAccountNumber())
                .build());
    }
    
    public static EmployeeRegDTO convertEntityToDTOs(Employee employee) {
        return EmployeeRegDTO.builder()
                .employeeEmailId(employee.getEmployeeEmailId())
                .employeeName(employee.getEmployeeName())
                .addresses(employee.getAddresses().stream()
                        .map(a -> AddressDTO.builder()
                                .addressCity(a.getAddressCity())
                                .addressState(a.getAddressState())
                                .addressType(a.getAddressType())
                                .build()).collect(Collectors.toList()))
                .technologies(employee.getTechnologies().stream()
                        .map(t -> TechnologyDTO.builder()
                                .technologyName(t.getTechnologyName())
                                .build()).toList())
                .bankAccountDTO(BankAccountDTO.builder()
                		.bankAccountNumber(employee.getBankAccount().getBankAccountNumber())
                		.bankIFSC(employee.getBankAccount().getBankIFSC())
                		.build())
                .build();
    }
}
