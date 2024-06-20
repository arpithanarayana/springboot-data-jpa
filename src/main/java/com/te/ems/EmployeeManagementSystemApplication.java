package com.te.ems;

import com.te.ems.entity.Technology;
import com.te.ems.repository.TechnologyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@RequiredArgsConstructor
@SpringBootApplication
public class EmployeeManagementSystemApplication {
	private final TechnologyRepository technologyRepository;

	public static void main(String[] args) {
		SpringApplication.run(EmployeeManagementSystemApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(){
		return args -> {
			if(!technologyRepository.existsById("java")) {
				technologyRepository.save(Technology.builder().technologyName("java").build());
				technologyRepository.save(Technology.builder().technologyName("python").build());
				technologyRepository.save(Technology.builder().technologyName("javascript").build());
				technologyRepository.save(Technology.builder().technologyName("c#").build());
			}
		};
	}
}
