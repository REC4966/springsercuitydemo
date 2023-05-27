package com.example.springsercuitydemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.springsercuitydemo.mapper")
public class SpringsercuitydemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringsercuitydemoApplication.class, args);
	}

}
