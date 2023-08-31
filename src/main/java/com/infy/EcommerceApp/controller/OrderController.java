package com.infy.EcommerceApp.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import com.infy.EcommerceApp.enums.OrderStatus;
import com.infy.EcommerceApp.model.Order;
import com.infy.EcommerceApp.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping("api")
public class OrderController {
    @Autowired
    OrderRepository orderRepository;

    public OrderController() {
    }

    @GetMapping({"/orders"})
    public ResponseEntity<ArrayList<Order>> getAllOrders() {
        try {
            ArrayList<Order> orders = (ArrayList)this.orderRepository.findAll();
            return orders.isEmpty() ? new ResponseEntity(HttpStatus.NO_CONTENT) : new ResponseEntity(orders, HttpStatus.OK);
        } catch (Exception var2) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping({"/orders/{id}"})
    public ResponseEntity<Order> getOrderById(@PathVariable("id") Long id) {
        Optional<Order> orderData = this.orderRepository.findById(id);
        return orderData.isPresent() ? new ResponseEntity((Order)orderData.get(), HttpStatus.OK) : new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @GetMapping({"/orders/{userId}"})
    public ResponseEntity<ArrayList<Order>> getOrdersByUserId(@PathVariable("userId") Integer userId) {
        try {
            ArrayList<Order> orders = this.orderRepository.findByUserUserId(userId);
            return orders.isEmpty() ? new ResponseEntity(HttpStatus.NO_CONTENT) : new ResponseEntity(orders, HttpStatus.OK);
        } catch (Exception var3) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping({"/orders"})
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        try {
            Order _order = (Order)this.orderRepository.save(new Order(OrderStatus.PENDING, LocalDate.now(), order.getOrderedProducts(), order.getUser()));
            return new ResponseEntity(_order, HttpStatus.CREATED);
        } catch (Exception var3) {
            return new ResponseEntity((MultiValueMap)null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping({"/orders/{id}"})
    public ResponseEntity<Order> updateOrder(@PathVariable("id") Long id, @RequestBody Order order) {
        Optional<Order> orderData = this.orderRepository.findById(id);
        if (orderData.isPresent()) {
            Order _order = (Order)orderData.get();
            _order.setOrderStatus(order.getOrderStatus());
            return new ResponseEntity((Order)this.orderRepository.save(_order), HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping({"/orders/{id}"})
    public ResponseEntity<HttpStatus> deleteOrder(@PathVariable("id") long id) {
        try {
            this.orderRepository.deleteById(id);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (Exception var4) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping({"/orders"})
    public ResponseEntity<HttpStatus> deleteAllOrders() {
        try {
            this.orderRepository.deleteAll();
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (Exception var2) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
