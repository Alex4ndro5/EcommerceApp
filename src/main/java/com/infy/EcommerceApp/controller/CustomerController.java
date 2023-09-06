package com.infy.EcommerceApp.controller;

import java.util.ArrayList;
import java.util.Optional;

import com.infy.EcommerceApp.model.Customer;
import com.infy.EcommerceApp.repository.CustomerRepository;
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

@RestController
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;

    public CustomerController() {
    }

    @GetMapping({"/customers"})
    public ResponseEntity<CollectionModel<EntityModel<Customer>>> getAllCustomers() {
        try {
            ArrayList<Customer> customers = (ArrayList)this.customerRepository.findAll();
            return customers.isEmpty() ? new ResponseEntity(HttpStatus.NO_CONTENT) : new ResponseEntity(customers, HttpStatus.OK);
        } catch (Exception var2) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping({"/customers/{id}"})
    public ResponseEntity<Customer> getCustomerById(@PathVariable("id") Long id) {
        Optional<Customer> customerData = this.customerRepository.findById(id);
        return customerData.isPresent() ? new ResponseEntity((Customer)customerData.get(), HttpStatus.OK) : new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @PostMapping({"/customers"})
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        try {
            Customer _customer = (Customer)this.customerRepository.save(new Customer(customer.getCustomerName(), customer.getCustomerPassword(), customer.getCustomerBirthdate(), customer.getCustomerEmail(), customer.getCustomerAddress()));
            return new ResponseEntity(_customer, HttpStatus.CREATED);
        } catch (Exception var3) {
            return new ResponseEntity((MultiValueMap)null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping({"/customers/{id}"})
    public ResponseEntity<Customer> updateCustomer(@PathVariable("id") Long id, @RequestBody Customer customer) {
        Optional<Customer> customerData = this.customerRepository.findById(id);
        if (customerData.isPresent()) {
            Customer _customer = (Customer)customerData.get();
            _customer.setCustomerName(customer.getCustomerName());
            _customer.setCustomerPassword(customer.getCustomerPassword());
            _customer.setCustomerBirthdate(customer.getCustomerBirthdate());
            _customer.setCustomerEmail(customer.getCustomerEmail());
            _customer.setCustomerAddress(customer.getCustomerAddress());
            return new ResponseEntity((Customer)this.customerRepository.save(_customer), HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping({"/customers/{id}"})
    public ResponseEntity<HttpStatus> deleteCustomer(@PathVariable("id") long id) {
        try {
            this.customerRepository.deleteById(id);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (Exception var4) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping({"/customers"})
    public ResponseEntity<HttpStatus> deleteAllCustomers() {
        try {
            this.customerRepository.deleteAll();
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (Exception var2) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
