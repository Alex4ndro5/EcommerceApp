package com.infy.EcommerceApp.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.infy.EcommerceApp.enums.CustomerGender;
import com.infy.EcommerceApp.enums.CustomerRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Entity
@Data
@Table(name = "customers")
public class Customer {
    @Id
    @Column(name = "customer_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;
    @Column(name = "customer_name", nullable = false)
    private String customerName;
    @Column(name = "customer_password")
    private String customerPassword;
    @Column(name = "customer_birthdate")
    private String customerBirthdate;
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
    @OneToMany(mappedBy = "customer",  cascade = CascadeType.ALL)
    private List<Order> customerOrders = new ArrayList<>();

    public Customer(String customerName, String customerPassword, String customerBirthdate, String customerEmail, String customerAddress) {
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
