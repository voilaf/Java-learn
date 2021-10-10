package com.example.tcc2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

@SpringBootApplication(exclude = MongoAutoConfiguration.class)
public class Tcc2ProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(Tcc2ProviderApplication.class, args);
	}
}
