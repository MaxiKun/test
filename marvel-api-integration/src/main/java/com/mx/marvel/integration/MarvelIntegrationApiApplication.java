package com.mx.marvel.integration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author MBL
 */

@SpringBootApplication
@ComponentScan(basePackages = "com.mx")
public class MarvelIntegrationApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MarvelIntegrationApiApplication.class, args);
	}
	

	
}
