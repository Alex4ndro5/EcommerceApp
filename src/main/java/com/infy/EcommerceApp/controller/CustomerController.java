package com.infy.EcommerceApp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.infy.EcommerceApp.assemblers.CustomerModelAssembler;
import com.infy.EcommerceApp.exceptions.CustomerNotFoundException;
import com.infy.EcommerceApp.model.Customer;
import com.infy.EcommerceApp.repository.CustomerRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerModelAssembler customerModelAssembler;

    public CustomerController() {
    }

    @GetMapping("/customers")
    public ResponseEntity<CollectionModel<EntityModel<Customer>>> getAllCustomers() {
        List<EntityModel<Customer>> customers = customerRepository.findAll().stream()
                .map(customerModelAssembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity
                .ok(CollectionModel.of(customers, linkTo(methodOn(CustomerController.class)
                        .getAllCustomers()).withSelfRel()));
    }

    @SneakyThrows
    @GetMapping("/customers/{id}")
    public ResponseEntity<EntityModel<Customer>> getCustomerById(@PathVariable("id") Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
        return ResponseEntity.ok(customerModelAssembler.toModel(customer));
    }

    @PostMapping("/customers")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {

        customer = customerRepository.save(customer);
        return ResponseEntity.created(linkTo(methodOn(CustomerController.class)
                        .getCustomerById(customer.getCustomerId())).toUri()).body(customer);
    }


    @PutMapping("/customers/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable("id") Long id, @RequestBody Customer customer) {
        Optional<Customer> customerData = customerRepository.findById(id);
        if (customerData.isPresent()) {
            Customer existingCustomer = customerData.get();
            existingCustomer.setCustomerName(customer.getCustomerName());
            existingCustomer.setCustomerPassword(customer.getCustomerPassword());
            existingCustomer.setCustomerBirthdate(customer.getCustomerBirthdate());
            existingCustomer.setCustomerEmail(customer.getCustomerEmail());
            existingCustomer.setCustomerAddress(customer.getCustomerAddress());

            return ResponseEntity.ok(customerRepository.save(existingCustomer));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/customers/{id}")
    public ResponseEntity<HttpStatus> deleteCustomer(@PathVariable("id") long id) {
        try {
            customerRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/customers")
    public ResponseEntity<HttpStatus> deleteAllCustomers() {
        try {
            customerRepository.deleteAll();
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}

