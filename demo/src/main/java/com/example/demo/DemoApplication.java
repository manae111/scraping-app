package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.SpringSecurityCoreVersion;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		String springSecurityVersion = SpringSecurityCoreVersion.getVersion();
        System.out.println("Spring Security Version: " + springSecurityVersion);
	}
}
