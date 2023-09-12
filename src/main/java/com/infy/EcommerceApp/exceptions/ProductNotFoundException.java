package com.infy.EcommerceApp.exceptions;

/**
 * Exception thrown when a product with a specified ID is not found.
 */
public class ProductNotFoundException extends Exception {

    /**
     * Constructs a new ProductNotFoundException with a message indicating the product ID that was not found.
     *
     * @param productId The ID of the product that was not found.
     */
    public ProductNotFoundException(Long productId){
        super();
    }
}
