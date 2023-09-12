package com.infy.EcommerceApp.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.infy.EcommerceApp.enums.OrderStatus;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.Min;
import lombok.*;


@NoArgsConstructor
@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    private Long orderId;
    @Column(name = "order_status")
    private OrderStatus orderStatus;
    @Column(name = "order_date")
    private LocalDate orderDate;
    @JsonBackReference(value = "orderCustomer")
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @JsonBackReference(value = "orderedProduct")
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @Min(value = 1, message = "Min quantity 1")
    @Column(name = "quantity")
    private Integer quantity;

    public Order(Customer customer, Product product, Integer quantity) {
        this.orderStatus = OrderStatus.IN_PROGRESS;
        this.orderDate = LocalDate.now();
        this.customer = customer;
        this.product = product;
        this.quantity = quantity;
    }
    public String toString() {
        return "Order{orderId=" + this.orderId + ", orderStatus=" + this.orderStatus + ", orderDate=" + this.orderDate + ", orderedProducts= " + this.product +  "quantity: " + quantity + ", customer= " + this.customer + "}";
    }
}
