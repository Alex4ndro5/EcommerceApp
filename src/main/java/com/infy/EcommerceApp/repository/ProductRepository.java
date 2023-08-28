package com.infy.EcommerceApp.repository;

import java.util.ArrayList;

import com.infy.EcommerceApp.enums.ProductCategory;
import com.infy.EcommerceApp.enums.ProductManufacturer;
import com.infy.EcommerceApp.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    ArrayList<Product> findByProductCategory(ProductCategory productCategory);

    ArrayList<Product> findByProductNameContaining(String productName);

    ArrayList<Product> findByProductManufacturer(ProductManufacturer productManufacturer);
}
