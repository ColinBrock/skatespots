package com.example.skatespots;

import com.example.skatespots.storage.StorageProperties;
import com.example.skatespots.storage.StorageService;
import com.google.maps.GeoApiContext;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)

public class SkatespotsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SkatespotsApplication.class, args);
	}

    @Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			storageService.init();
		};
	}
}
