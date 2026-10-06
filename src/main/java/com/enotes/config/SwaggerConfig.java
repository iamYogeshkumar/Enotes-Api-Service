package com.enotes.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI  openApi() {
		
		OpenAPI openAPI = new OpenAPI();
	    
		Info info = new Info();
		info.setTitle("Enotes APi");
		info.setDescription("Enotes APi");
		info.setVersion("1.0.0");
		info.setTermsOfService("http://www.coder.com");
		info.setContact(new Contact().email("ys4139887@gmail.com").name("Coder").url("http://www.coder.com")
				       );
		List<Server> of = List.of(
		new Server().description("Dev").url("http://localhost:8080/"),
		new Server().description("Test").url("http://localhost:8081/"),
        new Server().description("Prod").url("http://localhost:8082/"));
	     
		
		
		SecurityScheme securityScheme = new SecurityScheme().name("Authorization")
		.scheme("bearer").type(Type.HTTP)
		.bearerFormat("JWT").in(In.HEADER);
		
		Components components = new Components().addSecuritySchemes("Token", securityScheme);
		
		openAPI.setComponents(components);
		openAPI.setServers(of);
		openAPI.setInfo(info);
		openAPI.setSecurity(List.of(new SecurityRequirement().addList("Token")));
		
		
		
		return openAPI;
	}
}
