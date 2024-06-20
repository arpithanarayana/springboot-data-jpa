package com.te.ems.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class BankAccount {
	@Id
	private String bankAccountNumber;
	private String bankIFSC;
	
	@OneToOne(mappedBy = "bankAccount", cascade = CascadeType.ALL)
	private Employee employee;
}
