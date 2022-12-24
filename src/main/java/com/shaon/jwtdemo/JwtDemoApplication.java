package com.shaon.jwtdemo;

import com.shaon.jwtdemo.utility.SpringApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class JwtDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtDemoApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}


	@Bean
	public SpringApplicationContext applicationContext(){
		return new SpringApplicationContext();
	}


}
