package com.te.ems.entity;

public enum AddressType {
	PERMANENT("Permanent"), TEMPORARY("Temporary");

	String type;

	AddressType(String type) {
		this.type = type;
	}
}
