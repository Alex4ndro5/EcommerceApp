package com.infy.EcommerceApp.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.infy.EcommerceApp.controller.OrderController;
import com.infy.EcommerceApp.enums.OrderStatus;
import com.infy.EcommerceApp.model.Order;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

/**
 * A component for assembling representations of Order entities into EntityModel instances.
 */
@Component
public class OrderModelAssembler implements RepresentationModelAssembler<Order, EntityModel<Order>> {

    /**
     * Converts an Order entity into an EntityModel representation with appropriate links.
     *
     * @param order The Order entity to be converted.
     * @return An EntityModel representation of the Order with self and related links.
     */
    @Override
    public EntityModel<Order> toModel(Order order) {

        // Unconditional links to single-item resource and aggregate root

        EntityModel<Order> orderModel = EntityModel.of(order,
                linkTo(methodOn(OrderController.class).getOrderById(order.getOrderId())).withSelfRel(),
                linkTo(methodOn(OrderController.class).getAllOrders()).withRel("orders"));

        // Conditional links based on the state of the order

        if (order.getOrderStatus() == OrderStatus.IN_PROGRESS) {
            orderModel.add(linkTo(methodOn(OrderController.class).cancelOrder(order.getOrderId())).withRel("cancel"));
            orderModel.add(linkTo(methodOn(OrderController.class).completeOrder(order.getOrderId())).withRel("complete"));
        }

        return orderModel;
    }
}
