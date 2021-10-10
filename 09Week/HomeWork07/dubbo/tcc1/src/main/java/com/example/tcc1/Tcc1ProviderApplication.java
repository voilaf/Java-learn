package com.example.tcc1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

@SpringBootApplication(exclude = MongoAutoConfiguration.class)
public class Tcc1ProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(Tcc1ProviderApplication.class, args);
	}
}
