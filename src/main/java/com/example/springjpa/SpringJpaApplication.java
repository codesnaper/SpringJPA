package com.example.springjpa;

import com.example.springjpa.dao.IPersonRep;
import com.example.springjpa.dao.PersonRep;
import com.example.springjpa.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.sql.Date;
import java.util.Arrays;

@SpringBootApplication(scanBasePackages = "com.example")
public class SpringJpaApplication {
	public  static Logger logger = LoggerFactory.getLogger(SpringJpaApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(SpringJpaApplication.class, args);
	}

}
