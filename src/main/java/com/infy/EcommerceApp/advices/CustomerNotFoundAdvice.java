package com.infy.EcommerceApp.advices;

import com.infy.EcommerceApp.exceptions.CustomerNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Global exception handler for handling CustomerNotFoundException.
 */
@ControllerAdvice
public class CustomerNotFoundAdvice {

    /**
     * Handles CustomerNotFoundException and returns a response with the exception message.
     *
     * @param ex The CustomerNotFoundException instance.
     * @return A response message indicating that the customer was not found.
     */
    @ResponseBody
    @ExceptionHandler(CustomerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String CustomerNotFoundHandler(CustomerNotFoundException ex) {
        return ex.getMessage();
    }
}
