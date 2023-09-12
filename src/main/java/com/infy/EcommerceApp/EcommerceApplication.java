package com.infy.EcommerceApp;

import com.infy.EcommerceApp.config.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * Main class of an application.
 */
@EnableConfigurationProperties(RsaKeyProperties.class)
@SpringBootApplication
public class EcommerceApplication {
	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);
	}
}