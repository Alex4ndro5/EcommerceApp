package com.infy.EcommerceApp.assemblers;


import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.infy.EcommerceApp.model.Customer;
@Component
public class CustomerRepresentationModelAssembler implements SimpleRepresentationModelAssembler<Customer> {
    @Override
    public void addLinks(EntityModel<Customer> resource) {

    }

    @Override
    public void addLinks(CollectionModel<EntityModel<Customer>> resources) {

    }
}
