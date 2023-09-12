package com.infy.EcommerceApp.repository;

import java.util.ArrayList;

import com.infy.EcommerceApp.enums.ProductCategory;
import com.infy.EcommerceApp.enums.ProductManufacturer;
import com.infy.EcommerceApp.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing product entities.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Retrieves a list of products by their category.
     *
     * @param productCategory The category of products to retrieve.
     * @return An ArrayList of products matching the specified category.
     */
    ArrayList<Product> findByProductCategory(ProductCategory productCategory);

    /**
     * Retrieves a list of products by their name containing the specified keyword.
     *
     * @param productName The keyword to search for in product names.
     * @return An ArrayList of products matching the name keyword.
     */
    ArrayList<Product> findByProductNameContaining(String productName);

    /**
     * Retrieves a list of products by their manufacturer.
     *
     * @param productManufacturer The manufacturer of products to retrieve.
     * @return An ArrayList of products from the specified manufacturer.
     */
    ArrayList<Product> findByProductManufacturer(ProductManufacturer productManufacturer);
}