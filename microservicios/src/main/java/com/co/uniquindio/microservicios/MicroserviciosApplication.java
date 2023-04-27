package com.co.uniquindio.microservicios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.co.uniquindio.microservicios.config.SwaggerConfig;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@SpringBootApplication
public class MicroserviciosApplication{ //implements WebMvcConfigurer{

	public static void main(String[] args) {
		SpringApplication.run(MicroserviciosApplication.class, args);
	}
	
//	@Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
// 
//           registry.addResourceHandler("swagger-ui.html")
//                    .addResourceLocations("classpath:/META-INF/resources/");
// 
//    }
	
	 @Bean
	    public OpenAPI customOpenAPI() {
	        return new OpenAPI()
	                .info(new Info()
	                        .title("Electiva Microservicios -------")
	                        .version("0.11")
	                        .description("Taller de documentacion OpenApi Swagger")
	                        .termsOfService("http://swagger.io/terms/")
	                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));

	    }

}
