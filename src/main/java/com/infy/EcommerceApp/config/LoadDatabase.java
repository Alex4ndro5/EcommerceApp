package com.infy.EcommerceApp.config;

import com.infy.EcommerceApp.enums.OrderStatus;
import com.infy.EcommerceApp.enums.ProductCategory;
import com.infy.EcommerceApp.enums.ProductManufacturer;
import com.infy.EcommerceApp.model.Customer;
import com.infy.EcommerceApp.model.Order;
import com.infy.EcommerceApp.model.Product;
import com.infy.EcommerceApp.repository.CustomerRepository;
import com.infy.EcommerceApp.repository.OrderRepository;
import com.infy.EcommerceApp.repository.ProductRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
class LoadDatabase {
    private static final Logger log = LogManager.getLogger(LoadDatabase.class);

    /**
     * Initializes the database by populating it with initial data.
     *
     * @param productRepository  The repository for managing products.
     * @param customerRepository The repository for managing customers.
     * @param orderRepository    The repository for managing orders.
     * @return A CommandLineRunner that initializes the database.
     */
    @Bean
    CommandLineRunner initDatabase(ProductRepository productRepository, CustomerRepository customerRepository,
                                   OrderRepository orderRepository) {
        return args -> {
            Product p1 = new Product("Omen 15", BigDecimal.valueOf(1400.0), ProductCategory.LAPTOP,
                    ProductManufacturer.HP, "http://placehold.it/200x100");
            log.info("Preloading " + productRepository.save(p1));
            log.info("Preloading " + productRepository.save(new Product("Omen 15", BigDecimal.valueOf(1400.0), ProductCategory.LAPTOP, ProductManufacturer.HP, "http://placehold.it/200x100")));
            log.info("Preloading " + productRepository.save(new Product("IPhone 8", BigDecimal.valueOf(900.0), ProductCategory.PHONE, ProductManufacturer.APPLE, "http://placehold.it/200x100")));
            log.info("Preloading " + productRepository.save(new Product("Usb-C Charger", BigDecimal.valueOf(50.0), ProductCategory.OTHER, ProductManufacturer.OTHER, "http://placehold.it/200x100")));
            log.info("Preloading " + productRepository.save(new Product("Predator 7", BigDecimal.valueOf(1250.0), ProductCategory.LAPTOP, ProductManufacturer.ACER, "http://placehold.it/200x100")));
            log.info("Preloading " + productRepository.save(new Product("Galaxy S10", BigDecimal.valueOf(800.0), ProductCategory.PHONE, ProductManufacturer.SAMSUNG, "http://placehold.it/200x100")));
            log.info("Preloading " + productRepository.save(new Product("MacBook Pro", BigDecimal.valueOf(1800.0), ProductCategory.LAPTOP, ProductManufacturer.APPLE, "http://placehold.it/200x100")));
            log.info("Preloading " + productRepository.save(new Product("Wireless Mouse", BigDecimal.valueOf(30.0), ProductCategory.OTHER, ProductManufacturer.LENOVO, "http://placehold.it/200x100")));
            log.info("Preloading " + productRepository.save(new Product("ThinkPad X1", BigDecimal.valueOf(1500.0), ProductCategory.LAPTOP, ProductManufacturer.LENOVO, "http://placehold.it/200x100")));
            log.info("Preloading " + productRepository.save(new Product("HTC 9", BigDecimal.valueOf(700.0), ProductCategory.PHONE, ProductManufacturer.HTC, "http://placehold.it/200x100")));
            log.info("Preloading " + productRepository.save(new Product("Bluetooth Speaker", BigDecimal.valueOf(60.0), ProductCategory.OTHER, ProductManufacturer.OTHER, "http://placehold.it/200x100")));
            log.info("Preloading " + productRepository.save(new Product("Inspiron 15", BigDecimal.valueOf(900.0), ProductCategory.LAPTOP, ProductManufacturer.DELL, "http://placehold.it/200x100")));
            log.info("Preloading " + productRepository.save(new Product("Pixel 5", BigDecimal.valueOf(600.0), ProductCategory.PHONE, ProductManufacturer.GOOGLE, "http://placehold.it/200x100")));
            log.info("Preloading " + productRepository.save(new Product("Wireless Keyboard", BigDecimal.valueOf(40.0), ProductCategory.OTHER, ProductManufacturer.MICROSOFT, "http://placehold.it/200x100")));
            log.info("Preloading " + productRepository.save(new Product("ZenBook 14", BigDecimal.valueOf(1200.0), ProductCategory.LAPTOP, ProductManufacturer.ASUS, "http://placehold.it/200x100")));
            log.info("Preloading " + productRepository.save(new Product("IPhone 12", BigDecimal.valueOf(1100.0), ProductCategory.PHONE, ProductManufacturer.APPLE, "http://placehold.it/200x100")));
            log.info("Preloading " + productRepository.save(new Product("Power Bank", BigDecimal.valueOf(20.0), ProductCategory.OTHER, ProductManufacturer.OTHER, "http://placehold.it/200x100")));
            log.info("Preloading " + productRepository.save(new Product("Aspire 5", BigDecimal.valueOf(700.0), ProductCategory.LAPTOP, ProductManufacturer.ACER, "http://placehold.it/200x100")));
            log.info("Preloading " + productRepository.save(new Product("Moto G9", BigDecimal.valueOf(300.0), ProductCategory.PHONE, ProductManufacturer.MOTOROLA, "http://placehold.it/200x100")));
            log.info("Preloading " + productRepository.save(new Product("Headphones", BigDecimal.valueOf(80.0), ProductCategory.OTHER, ProductManufacturer.SONY, "http://placehold.it/200x100")));

            customerRepository.deleteAll();
            Customer c1 = new Customer("Sofia", "p4ssw0rd", "21-04-2001","sofia@mail.com","Bangalore, India");
            log.info("Adding customer: " + customerRepository.save(c1));
            log.info("Adding customer: " + customerRepository.save(new Customer("Aleks", "p4ssw0rd", "21-04-2000","alek.mail@outlook.com","Bangalore, India")));


            log.info("Creating order: " + orderRepository.save(new Order(c1, p1, 2)));
        };
    }
}
