package com.dyz.intellig.service.intelligserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.dyz.intellig.service.intelligserver.dao")
public class IntelligServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(IntelligServerApplication.class, args);
	}

}
