package com.infy.EcommerceApp.repository;

import com.infy.EcommerceApp.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * Repository interface for managing customer entities.
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
