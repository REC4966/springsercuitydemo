package com.example.springsercuitydemo;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.springsercuitydemo.mapper.UserMapper;
import com.example.springsercuitydemo.mapper.menuMapper;

@SpringBootTest
class SpringsercuitydemoApplicationTests {

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private menuMapper menumapper;

	@Test
	void contextLoads() {
		List<String> selectPermsByUserid = menumapper.selectPermsByUserid(1);
		System.out.println(selectPermsByUserid);
		System.out.println("Hello World!");
	}

}
