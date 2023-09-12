package com.infy.EcommerceApp.advices;

import com.infy.EcommerceApp.exceptions.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Global exception handler for handling ProductNotFoundException.
 */
@ControllerAdvice
public class ProductNotFoundAdvice {

    /**
     * Handles ProductNotFoundException and returns a response with the exception message.
     *
     * @param ex The ProductNotFoundException instance.
     * @return A response message indicating that the product was not found.
     */
    @ResponseBody
    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String ProductNotFoundHandler(ProductNotFoundException ex) {
        return ex.getMessage();
    }
}