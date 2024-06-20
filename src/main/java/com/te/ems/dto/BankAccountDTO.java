package com.te.ems.dto;

import lombok.Builder;

@Builder
public record BankAccountDTO(String bankAccountNumber,
                             String bankIFSC) {
}
