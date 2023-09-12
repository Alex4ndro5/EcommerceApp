package com.infy.EcommerceApp.exceptions;

/**
 * Exception thrown when a customer with a specified ID is not found.
 */
public class CustomerNotFoundException extends Exception {

    /**
     * Constructs a new CustomerNotFoundException with a message indicating the customer ID that was not found.
     *
     * @param customerId The ID of the customer that was not found.
     */

    public CustomerNotFoundException(Long customerId){
        super();
    }
}
