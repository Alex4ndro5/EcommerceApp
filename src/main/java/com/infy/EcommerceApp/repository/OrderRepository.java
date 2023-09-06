package com.infy.EcommerceApp.repository;

import java.util.ArrayList;

import com.infy.EcommerceApp.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    ArrayList<Order> findByCustomerCustomerId(Integer customerId);
}
