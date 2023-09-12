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

    /**
     * Default constructor for OrderController.
     */
    public OrderController() {
    }

    /**
     * Retrieves all orders and returns them as a ResponseEntity with HATEOAS links.
     *
     * @return A ResponseEntity containing a collection of EntityModel instances representing orders.
     */
    @GetMapping({"/orders"})
    public ResponseEntity<CollectionModel<EntityModel<Order>>> getAllOrders() {
        List<EntityModel<Order>> orders = orderRepository.findAll().stream() //
                .map(orderModelAssembler::toModel) //
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(orders, linkTo(methodOn(OrderController.class)
                .getAllOrders()).withSelfRel()));
    }

    /**
     * Retrieves an order by its ID and returns it as a ResponseEntity with HATEOAS links.
     *
     * @param id The ID of the order to retrieve.
     * @return A ResponseEntity containing an EntityModel representing the order.
     * @throws OrderNotFoundException If the order with the given ID is not found.
     */
    @SneakyThrows
    @GetMapping({"/orders/{id}"})
    public ResponseEntity<EntityModel<Order>> getOrderById(@PathVariable("id") Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        return ResponseEntity.ok(orderModelAssembler.toModel(order));
    }
    /**
     * Creates a new order and returns it as a ResponseEntity with HATEOAS links.
     *
     * @param order The order to create.
     * @return A ResponseEntity containing the created order and a link to its resource.
     */
    @PostMapping("/orders/create")
    ResponseEntity<EntityModel<Order>> createOrder(@RequestBody Order order) {

        order.setOrderStatus(OrderStatus.IN_PROGRESS);
        Order newOrder = orderRepository.save(order);

        return ResponseEntity //
                .created(linkTo(methodOn(OrderController.class)
                        .getOrderById(newOrder.getOrderId())).toUri()) //
                .body(orderModelAssembler.toModel(newOrder));
    }
    /**
     * Marks an order as completed and returns it as a ResponseEntity with HATEOAS links.
     *
     * @param id The ID of the order to complete.
     * @return A ResponseEntity containing the completed order if successful, or an error response if the order is not in progress.
     * @throws OrderNotFoundException If the order with the given ID is not found.
     */
    @SneakyThrows
    @PutMapping({"/orders/{id}/complete"})
    public ResponseEntity<?> completeOrder(@PathVariable("id") Long id) {
        Order order = orderRepository.findById(id) //
                .orElseThrow(() -> new OrderNotFoundException(id));
        if (order.getOrderStatus() == OrderStatus.IN_PROGRESS) {
            order.setOrderStatus(OrderStatus.COMPLETED);
            return ResponseEntity.ok(orderModelAssembler.toModel(orderRepository.save(order)));
        }
        else {
            return ResponseEntity //
                    .status(HttpStatus.METHOD_NOT_ALLOWED) //
                    .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE) //
                    .body(Problem.create() //
                            .withTitle("Method not allowed") //
                            .withDetail("You can't complete an order that is in the "
                                    + order.getOrderStatus() + " status"));        }
        }
    /**
     * Cancels an order and returns it as a ResponseEntity with HATEOAS links.
     *
     * @param id The ID of the order to cancel.
     * @return A ResponseEntity containing the canceled order if successful, or an error response if the order is not in progress.
     * @throws OrderNotFoundException If the order with the given ID is not found.
     */
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
                        .withDetail("You can't cancel an order that is in the "
                                + order.getOrderStatus() + " status"));
    }

}
