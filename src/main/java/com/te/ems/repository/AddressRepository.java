package com.te.ems.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.te.ems.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Integer>{

}
