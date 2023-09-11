package com.infy.EcommerceApp.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.infy.EcommerceApp.enums.CustomerGender;
import com.infy.EcommerceApp.enums.CustomerRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Entity
@Data
@Table(name = "customers")
public class Customer {
    @Id
    @Column(name = "customer_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long customerId;
    @Column(name = "customer_name", nullable = false)
    private String customerName;
    @Column(name = "customer_password")
    private String customerPassword;
    @Column(name = "customer_birthdate")
    @JsonFormat(pattern = "dd/MM/yy")
    private LocalDate customerBirthdate;
    @Column(name = "customer_email", nullable = false, unique = true)
    @Email(message = "Email should be valid")
    private String customerEmail;
    @Column(name = "customer_address", nullable = false)
    private String customerAddress;
    @Column(name = "customer_gender")
    private CustomerGender customerGender;
    @Column(name = "customer_role")
    private CustomerRole customerRole;
    @Column(name = "customer_orders")
    @OneToMany(mappedBy = "customer")
    private List<Order> customerOrders;

    ObjectMapper mapper = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .build();

    public Customer(String customerName, String customerPassword, LocalDate customerBirthdate, String customerEmail, String customerAddress) {
        this.customerRole = CustomerRole.CUSTOMER;
        this.customerName = customerName;
        this.customerPassword = customerPassword;
        this.customerBirthdate = customerBirthdate;
        this.customerEmail = customerEmail;
        this.customerAddress = customerAddress;
    }

    public String toString() {
        return "Customer{customerId=" + this.customerId + ", customerName='" + this.customerName + "', customerEmail='" + this.customerEmail + "', customerAddress='" + this.customerAddress + "'}";
    }
}
