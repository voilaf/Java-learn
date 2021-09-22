package com.example.shardingsphere;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.example.shardingsphere.dao"})
public class ShardingsphereApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShardingsphereApplication.class, args);
	}

}
