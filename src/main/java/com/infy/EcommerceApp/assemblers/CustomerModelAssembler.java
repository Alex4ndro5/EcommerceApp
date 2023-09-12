package com.infy.EcommerceApp.assemblers;


import com.infy.EcommerceApp.controller.CustomerController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.infy.EcommerceApp.model.Customer;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * A component for assembling representations of Customer entities into EntityModel instances.
 */
@Component
public class CustomerModelAssembler implements RepresentationModelAssembler<Customer, EntityModel<Customer>> {

    /**
     * Converts a Customer entity into an EntityModel representation with appropriate links.
     *
     * @param customer The Customer entity to be converted.
     * @return An EntityModel representation of the Customer with self and related links.
     */
    @Override
    public EntityModel<Customer> toModel(Customer customer) {
        return EntityModel.of(customer, //
                linkTo(methodOn(CustomerController.class).getCustomerById(customer.getCustomerId())).withSelfRel(),
                linkTo(methodOn(CustomerController.class).getAllCustomers()).withRel("customers"));
    }
}
