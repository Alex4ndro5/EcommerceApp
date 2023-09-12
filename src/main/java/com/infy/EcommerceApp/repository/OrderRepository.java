package com.infy.EcommerceApp.repository;

import com.infy.EcommerceApp.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * Repository interface for managing order entities.
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
