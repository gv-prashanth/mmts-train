package com.vadrin.mmtstrain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class MmtsTrainApplication {

	public static void main(String[] args) {
		SpringApplication.run(MmtsTrainApplication.class, args);
	}
	
	@Bean
	public RestTemplate constructRestTemplate(RestTemplateBuilder restTemplateBuilder) {
    return restTemplateBuilder.build();
	}
}
