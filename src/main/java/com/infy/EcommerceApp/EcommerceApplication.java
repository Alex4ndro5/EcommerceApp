package com.infy.EcommerceApp;


import java.math.BigDecimal;

import com.infy.EcommerceApp.enums.ProductCategory;
import com.infy.EcommerceApp.enums.ProductManufacturer;
import com.infy.EcommerceApp.model.Product;
import com.infy.EcommerceApp.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EcommerceApplication {
	public EcommerceApplication() {
	}

	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(ProductRepository productRepository) {
		return (args) -> {
			productRepository.save(new Product("Omen 15", BigDecimal.valueOf(1400.0), ProductCategory.LAPTOP, ProductManufacturer.HP, "http://placehold.it/200x100"));
			productRepository.save(new Product("IPhone 8", BigDecimal.valueOf(900.0), ProductCategory.PHONE, ProductManufacturer.APPLE, "http://placehold.it/200x100"));
			productRepository.save(new Product("Usb-C Charger", BigDecimal.valueOf(50.0), ProductCategory.OTHER, ProductManufacturer.OTHER, "http://placehold.it/200x100"));
			productRepository.save(new Product("Predator 7", BigDecimal.valueOf(1250.0), ProductCategory.LAPTOP, ProductManufacturer.ACER, "http://placehold.it/200x100"));
			productRepository.save(new Product("Galaxy S10", BigDecimal.valueOf(800.0), ProductCategory.PHONE, ProductManufacturer.SAMSUNG, "http://placehold.it/200x100"));
			productRepository.save(new Product("MacBook Pro", BigDecimal.valueOf(1800.0), ProductCategory.LAPTOP, ProductManufacturer.APPLE, "http://placehold.it/200x100"));
			productRepository.save(new Product("Wireless Mouse", BigDecimal.valueOf(30.0), ProductCategory.OTHER, ProductManufacturer.LENOVO, "http://placehold.it/200x100"));
			productRepository.save(new Product("ThinkPad X1", BigDecimal.valueOf(1500.0), ProductCategory.LAPTOP, ProductManufacturer.LENOVO, "http://placehold.it/200x100"));
			productRepository.save(new Product("HTC 9", BigDecimal.valueOf(700.0), ProductCategory.PHONE, ProductManufacturer.HTC, "http://placehold.it/200x100"));
			productRepository.save(new Product("Bluetooth Speaker", BigDecimal.valueOf(60.0), ProductCategory.OTHER, ProductManufacturer.OTHER, "http://placehold.it/200x100"));
			productRepository.save(new Product("Inspiron 15", BigDecimal.valueOf(900.0), ProductCategory.LAPTOP, ProductManufacturer.DELL, "http://placehold.it/200x100"));
			productRepository.save(new Product("Pixel 5", BigDecimal.valueOf(600.0), ProductCategory.PHONE, ProductManufacturer.GOOGLE, "http://placehold.it/200x100"));
			productRepository.save(new Product("Wireless Keyboard", BigDecimal.valueOf(40.0), ProductCategory.OTHER, ProductManufacturer.MICROSOFT, "http://placehold.it/200x100"));
			productRepository.save(new Product("ZenBook 14", BigDecimal.valueOf(1200.0), ProductCategory.LAPTOP, ProductManufacturer.ASUS, "http://placehold.it/200x100"));
			productRepository.save(new Product("IPhone 12", BigDecimal.valueOf(1100.0), ProductCategory.PHONE, ProductManufacturer.APPLE, "http://placehold.it/200x100"));
			productRepository.save(new Product("Power Bank", BigDecimal.valueOf(20.0), ProductCategory.OTHER, ProductManufacturer.OTHER, "http://placehold.it/200x100"));
			productRepository.save(new Product("Aspire 5", BigDecimal.valueOf(700.0), ProductCategory.LAPTOP, ProductManufacturer.ACER, "http://placehold.it/200x100"));
			productRepository.save(new Product("Moto G9", BigDecimal.valueOf(300.0), ProductCategory.PHONE, ProductManufacturer.MOTOROLA, "http://placehold.it/200x100"));
			productRepository.save(new Product("Headphones", BigDecimal.valueOf(80.0), ProductCategory.OTHER, ProductManufacturer.SONY, "http://placehold.it/200x100"));
		};
	}
}