package com.te.ems.repository;

import com.te.ems.entity.Employee;
import com.te.ems.entity.Technology;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TechnologyRepository extends JpaRepository<Technology, String>{

}
