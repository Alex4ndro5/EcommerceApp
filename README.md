# EcommerceApp - Java Spring Boot Rest API

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.1.2-brightgreen)
![Java](https://img.shields.io/badge/Java-17-red)
![ELK Stack](https://img.shields.io/badge/ELK%20Stack-7.15.0-blue)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-12.5-blue)
![License](https://img.shields.io/badge/License-MIT-orange)

This is a Java Spring Boot Rest API project with ELK Stack (Elasticsearch, Logstash, Kibana) implementation, authentication, authorization, and PostgreSQL database integration. It provides a foundation for building RESTful APIs with security and logging capabilities.

## Features

- Java Spring Boot 3.1.2
- Authentication and Authorization
- PostgreSQL Database Integration
- ELK Stack (Elasticsearch, Logstash, Kibana) Implementation
- Log4j2 for Logging

## Prerequisites

Before you begin, ensure you have met the following requirements:

- Java 17
- [Spring Boot](https://spring.io/projects/spring-boot) 3.1.2
- [PostgreSQL](https://www.postgresql.org/) 12.5 (You can change the version as needed in the `pom.xml` file)
- ELK Stack (Elasticsearch, Logstash, Kibana) 7.15.0
- IDE for Java development (e.g., [IntelliJ IDEA](https://www.jetbrains.com/idea/))

## Getting Started

To get started with this project, follow these steps:

1. Clone the repository to your local machine:

```shell
git clone https://github.com/Alex4ndro5/EcommerceApp.git
```
2. Open the project in your favorite Java IDE.

3. Configure your PostgreSQL database connection in application.properties.

4. Run the application.

5. Access the API endpoints and explore the functionalities.

## API Endpoints - order controller example

### 1. Get All Orders

- **Method**: GET
- **Endpoint**: `/orders`
- **Description**: Retrieve all orders and return them as a ResponseEntity with HATEOAS links.

### 2. Get Order by ID

- **Method**: GET
- **Endpoint**: `/orders/{id}`
- **Description**: Retrieve an order by its ID and return it as a ResponseEntity with HATEOAS links.
- **Parameters**: 
  - `id` (Order ID)

### 3. Create a New Order

- **Method**: POST
- **Endpoint**: `/orders/create`
- **Description**: Create a new order and return it as a ResponseEntity with HATEOAS links.

### 4. Complete an Order

- **Method**: PUT
- **Endpoint**: `/orders/{id}/complete`
- **Description**: Mark an order as completed and return it as a ResponseEntity with HATEOAS links.
- **Parameters**: 
  - `id` (Order ID)

### 5. Cancel an Order

- **Method**: DELETE
- **Endpoint**: `/orders/{id}/cancel`
- **Description**: Cancel an order and return it as a ResponseEntity with HATEOAS links.
- **Parameters**: 
  - `id` (Order ID)

## Acknowledgments
Thanks to the Spring Boot and ELK Stack communities for providing excellent tools and resources.
