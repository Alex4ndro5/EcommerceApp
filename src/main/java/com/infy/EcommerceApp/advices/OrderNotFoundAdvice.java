package com.infy.EcommerceApp.advices;

import com.infy.EcommerceApp.exceptions.OrderNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * Global exception handler for handling OrderNotFoundException.
 */
@ControllerAdvice
public class OrderNotFoundAdvice {

    /**
     * Handles OrderNotFoundException and returns a response with the exception message.
     *
     * @param ex The OrderNotFoundException instance.
     * @return A response message indicating that the order was not found.
     */
    @ResponseBody
    @ExceptionHandler(OrderNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String OrderNotFoundHandler(OrderNotFoundException ex) {
        return ex.getMessage();
    }
}
