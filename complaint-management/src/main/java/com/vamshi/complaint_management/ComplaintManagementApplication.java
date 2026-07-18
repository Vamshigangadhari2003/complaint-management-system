package com.vamshi.complaint_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ComplaintManagementApplication {
	public static void main(String[] args) {
		SpringApplication.run(ComplaintManagementApplication.class, args);
	}
}