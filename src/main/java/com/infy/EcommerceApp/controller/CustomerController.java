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

/**
 * Controller class responsible for managing customer-related endpoints.
 */
@RestController
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerModelAssembler customerModelAssembler;

    /**
     * Default constructor for CustomerController.
     */
    public CustomerController() {
    }

    /**
     * Retrieves all customers and returns them as a ResponseEntity with HATEOAS links.
     *
     * @return A ResponseEntity containing a collection of EntityModel instances representing customers.
     */
    @GetMapping("/customers")
    public ResponseEntity<CollectionModel<EntityModel<Customer>>> getAllCustomers() {
        List<EntityModel<Customer>> customers = customerRepository.findAll().stream()
                .map(customerModelAssembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity
                .ok(CollectionModel.of(customers, linkTo(methodOn(CustomerController.class)
                        .getAllCustomers()).withSelfRel()));
    }

    /**
     * Retrieves a customer by their ID and returns it as a ResponseEntity with HATEOAS links.
     *
     * @param id The ID of the customer to retrieve.
     * @return A ResponseEntity containing an EntityModel representing the customer.
     * @throws CustomerNotFoundException If the customer with the given ID is not found.
     */
    @SneakyThrows
    @GetMapping("/customers/{id}")
    public ResponseEntity<EntityModel<Customer>> getCustomerById(@PathVariable("id") Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
        return ResponseEntity.ok(customerModelAssembler.toModel(customer));
    }

    /**
     * Creates a new customer and returns a ResponseEntity with the created customer and HATEOAS links.
     *
     * @param customer The customer to create.
     * @return A ResponseEntity containing the created customer and a link to its resource.
     */
    @PostMapping("/customers")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {

        customer = customerRepository.save(customer);
        return ResponseEntity.created(linkTo(methodOn(CustomerController.class)
                .getCustomerById(customer.getCustomerId())).toUri()).body(customer);
    }


    /**
     * Updates an existing customer with the provided data.
     *
     * @param id       The ID of the customer to update.
     * @param customer The updated customer data.
     * @return A ResponseEntity containing the updated customer if successful, or a not-found response if the customer does not exist.
     */
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

    /**
     * Deletes a customer by their ID.
     *
     * @param id The ID of the customer to delete.
     * @return A ResponseEntity with a success status if the customer is deleted, or an internal server error if an exception occurs.
     */
    @DeleteMapping("/customers/{id}")
    public ResponseEntity<HttpStatus> deleteCustomer(@PathVariable("id") long id) {
        try {
            customerRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Deletes all customers.
     *
     * @return A ResponseEntity with a success status if all customers are deleted, or an internal server error if an exception occurs.
     */
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

