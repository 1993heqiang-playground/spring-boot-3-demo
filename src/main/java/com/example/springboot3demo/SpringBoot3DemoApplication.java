package com.example.springboot3demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class SpringBoot3DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBoot3DemoApplication.class, args);
	}

}
