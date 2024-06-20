package com.te.ems.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.te.ems.entity.Employee;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer>{

    @Query("select e from Employee e join fetch e.addresses a where a.addressCity = ?1") 
    List<Employee> findByEmployeeCity(String addressCity);
    
    @Query("select e from Employee e join fetch e.technologies t where t.technologyName = ?1")
    List<Employee> findByEmployeeTechnology(String technologyName);
    
    @Query("select e from Employee e join fetch e.bankAccount b where b.bankIFSC = ?1")
    List<Employee> findByEmployeeIFSC(String bankIFSC);
}
