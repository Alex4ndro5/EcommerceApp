package com.infy.EcommerceApp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "orderedProducts"
)
public class OrderedProduct {
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    private Long orderedProductId;
    @Column(
            name = "quantity"
    )
    private @Min(
            value = 1L,
            message = "Quantity should be greater than 0"
    ) Integer quantity;
    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false
    )
    @JoinColumn(
            name = "orderId",
            nullable = false
    )
    @OnDelete(
            action = OnDeleteAction.CASCADE
    )
    @JsonIgnore
    private Order order;
    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false
    )
    @JoinColumn(
            name = "productId",
            nullable = false
    )
    @OnDelete(
            action = OnDeleteAction.CASCADE
    )
    @JsonIgnore
    private Product product;
}