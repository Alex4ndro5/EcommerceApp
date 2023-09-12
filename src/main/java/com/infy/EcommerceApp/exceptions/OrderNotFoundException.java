package com.infy.EcommerceApp.exceptions;

/**
 * Exception thrown when an order with a specified ID is not found.
 */
public class OrderNotFoundException extends Exception {

    /**
     * Constructs a new OrderNotFoundException with a message indicating the order ID that was not found.
     *
     * @param orderId The ID of the order that was not found.
     */
    public OrderNotFoundException(Long orderId){
        super();
    }
}
