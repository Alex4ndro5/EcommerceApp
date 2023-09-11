package com.infy.EcommerceApp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.infy.EcommerceApp.enums.OrderStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.List;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@NoArgsConstructor
@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderId;
    @Column(name = "order_status")
    private OrderStatus orderStatus;
    @Column(name = "order_date")
    private LocalDate orderDate;
    @OneToMany(mappedBy = "order")
    private List<OrderedProduct> orderedProducts;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customerId", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Customer customer;

    public Order(OrderStatus orderStatus, LocalDate orderDate, List<OrderedProduct> orderedProducts, Customer customer) {
        this.orderStatus = orderStatus;
        this.orderDate = orderDate;
        this.orderedProducts = orderedProducts;
        this.customer = customer;
    }

    public String toString() {
        return "Order{orderId=" + this.orderId + ", orderStatus=" + this.orderStatus + ", orderDate=" + this.orderDate + ", orderedProducts=" + this.orderedProducts + ", customer=" + this.customer + "}";
    }
}
