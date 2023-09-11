package com.infy.EcommerceApp.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.infy.EcommerceApp.enums.ProductCategory;
import com.infy.EcommerceApp.enums.ProductManufacturer;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@Data
@Entity
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Table(name = "products")
public class Product {
    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "product_price", nullable = false)
    @DecimalMin(value = "0.0", inclusive = false, message = "Price should be greater than 0")
    private BigDecimal productPrice;

    @Column(name = "product_category")
    private ProductCategory productCategory;

    @Column(name = "product_manufacturer")
    private ProductManufacturer productManufacturer;

    @Column(name = "product_picture_url")
    private String productPictureUrl;

    public Product(String productName, BigDecimal productPrice, ProductCategory productCategory, ProductManufacturer productManufacturer, String productPictureUrl) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productCategory = productCategory;
        this.productManufacturer = productManufacturer;
        this.productPictureUrl = productPictureUrl;
    }

    public String toString() {
        return "Product{productName='" + this.productName + "', productPrice=" + this.productPrice + ", productCategory=" + this.productCategory + ", productManufacturer=" + this.productManufacturer + "}";
    }
}