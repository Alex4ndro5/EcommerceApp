package com.infy.EcommerceApp.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.infy.EcommerceApp.assemblers.OrderModelAssembler;
import com.infy.EcommerceApp.enums.OrderStatus;
import com.infy.EcommerceApp.exceptions.OrderNotFoundException;
import com.infy.EcommerceApp.model.Order;
import com.infy.EcommerceApp.repository.OrderRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class OrderController {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderModelAssembler orderModelAssembler;

    public OrderController() {
    }

    @GetMapping({"/orders"})
    public ResponseEntity<CollectionModel<EntityModel<Order>>> getAllOrders() {
        List<EntityModel<Order>> orders = orderRepository.findAll().stream() //
                .map(orderModelAssembler::toModel) //
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(orders, linkTo(methodOn(OrderController.class).getAllOrders()).withSelfRel()));
    }

    @SneakyThrows
    @GetMapping({"/orders/{id}"})
    public ResponseEntity<EntityModel<Order>> getOrderById(@PathVariable("id") Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        return ResponseEntity.ok(orderModelAssembler.toModel(order));
    }

    @PostMapping("/orders/create")
    ResponseEntity<EntityModel<Order>> createOrder(@RequestBody Order order) {

        order.setOrderStatus(OrderStatus.IN_PROGRESS);
        Order newOrder = orderRepository.save(order);

        return ResponseEntity //
                .created(linkTo(methodOn(OrderController.class).getOrderById(newOrder.getOrderId())).toUri()) //
                .body(orderModelAssembler.toModel(newOrder));
    }

    @SneakyThrows
    @PutMapping({"/orders/{id}/complete"})
    public ResponseEntity<?> completeOrder(@PathVariable("id") Long id) {
        Order order = orderRepository.findById(id) //
                .orElseThrow(() -> new OrderNotFoundException(id));
        if (order.getOrderStatus() == OrderStatus.IN_PROGRESS) {
            order.setOrderStatus(OrderStatus.COMPLETED);
            return ResponseEntity.ok(orderModelAssembler.toModel(orderRepository.save(order)));
        }
        return ResponseEntity //
                .status(HttpStatus.METHOD_NOT_ALLOWED) //
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE) //
                .body(Problem.create() //
                        .withTitle("Method not allowed") //
                        .withDetail("You can't complete an order that is in the " + order.getOrderStatus() + " status"));
    }

    @SneakyThrows
    @DeleteMapping("/orders/{id}/cancel")
    public ResponseEntity<?> cancelOrder(@PathVariable Long id) {

        Order order = orderRepository.findById(id) //
                .orElseThrow(() -> new OrderNotFoundException(id));

        if (order.getOrderStatus() == OrderStatus.IN_PROGRESS) {
            order.setOrderStatus(OrderStatus.CANCELLED);
            return ResponseEntity.ok(orderModelAssembler.toModel(orderRepository.save(order)));
        }

        return ResponseEntity //
                .status(HttpStatus.METHOD_NOT_ALLOWED) //
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE) //
                .body(Problem.create() //
                        .withTitle("Method not allowed") //
                        .withDetail("You can't cancel an order that is in the " + order.getOrderStatus() + " status"));
    }

}
