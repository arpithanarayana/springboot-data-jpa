package com.te.ems.dto;

import lombok.Builder;

import java.util.List;
@Builder
public record EmployeeRegDTO
        (String employeeName,
         String employeeEmailId,
         BankAccountDTO bankAccountDTO,

         List<AddressDTO> addresses,
         List<TechnologyDTO> technologies) {
}
