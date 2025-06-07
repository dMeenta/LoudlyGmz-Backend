package com.example.loudlygmz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(
	info = @Info(
		title = "LoudlyGmz - Red Social - REST API Documentation",
		description = "Proyecto de demostración de habilidades con Java y SpringBoot, utilizando patrones MVC, POO, Clean Architecture con el fin de escalar a microservicios",
		version = "v1",
		contact = @Contact(
			name = "Diego Quipuzco",
			email = "diegoaqb.123@gmail.com",
			url = "https://www.linkedin.com/in/dquipuzco/"
		)
	)
)
public class LoudlygmzApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoudlygmzApplication.class, args);
	}

}
