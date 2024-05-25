package com.att.tdp.bisbis10;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Bisbis10Application {

	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(Bisbis10Application.class, args);
	}

}
