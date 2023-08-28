package com.infy.EcommerceApp.controller;

import java.util.ArrayList;
import java.util.Optional;

import com.infy.EcommerceApp.enums.ProductCategory;
import com.infy.EcommerceApp.enums.ProductManufacturer;
import com.infy.EcommerceApp.model.Product;
import com.infy.EcommerceApp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    public ProductController() {
    }

    @GetMapping({"/products"})
    public ResponseEntity<ArrayList<Product>> getAllProducts() {
        try {
            ArrayList<Product> products = (ArrayList)this.productRepository.findAll();
            return products.isEmpty() ? new ResponseEntity(HttpStatus.NO_CONTENT) : new ResponseEntity(products, HttpStatus.OK);
        } catch (Exception var2) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping({"/products/{id}"})
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long id) {
        Optional<Product> productData = this.productRepository.findById(id);
        return productData.isPresent() ? new ResponseEntity((Product)productData.get(), HttpStatus.OK) : new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @GetMapping({"/products/category/{category}"})
    public ResponseEntity<ArrayList<Product>> getProductsByCategory(@PathVariable("category") String category) {
        try {
            ProductCategory productCategory = ProductCategory.valueOf(category.toUpperCase());

            try {
                ArrayList<Product> products = this.productRepository.findByProductCategory(productCategory);
                return products.isEmpty() ? new ResponseEntity(HttpStatus.NO_CONTENT) : new ResponseEntity(products, HttpStatus.OK);
            } catch (Exception var4) {
                return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception var5) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping({"/products/name/{name}"})
    public ResponseEntity<ArrayList<Product>> getProductsByName(@PathVariable("name") String name) {
        try {
            ArrayList<Product> products = this.productRepository.findByProductNameContaining(name);
            return products.isEmpty() ? new ResponseEntity(HttpStatus.NO_CONTENT) : new ResponseEntity(products, HttpStatus.OK);
        } catch (Exception var3) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping({"/products/manufacturer/{manufacturer}"})
    public ResponseEntity<ArrayList<Product>> getProductsByManufacturer(@PathVariable("manufacturer") String manufacturer) {
        try {
            ProductManufacturer productManufacturer = ProductManufacturer.valueOf(manufacturer.toUpperCase());

            try {
                ArrayList<Product> products = this.productRepository.findByProductManufacturer(productManufacturer);
                return products.isEmpty() ? new ResponseEntity(HttpStatus.NO_CONTENT) : new ResponseEntity(products, HttpStatus.OK);
            } catch (Exception var4) {
                return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception var5) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping({"/products"})
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        try {
            Product _product = (Product)this.productRepository.save(new Product(product.getProductName(), product.getProductPrice(), product.getProductCategory(), product.getProductManufacturer(), product.getProductPictureUrl()));
            return new ResponseEntity(_product, HttpStatus.CREATED);
        } catch (Exception var3) {
            return new ResponseEntity((MultiValueMap)null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping({"/products/{id}"})
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Long id, @RequestBody Product product) {
        Optional<Product> productData = this.productRepository.findById(id);
        if (productData.isPresent()) {
            Product _product = (Product)productData.get();
            _product.setProductPrice(product.getProductPrice());
            _product.setProductName(product.getProductName());
            _product.setProductCategory(product.getProductCategory());
            _product.setProductManufacturer(product.getProductManufacturer());
            _product.setProductPictureUrl(product.getProductPictureUrl());
            return new ResponseEntity((Product)this.productRepository.save(_product), HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping({"/products/{id}"})
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable("id") long id) {
        try {
            this.productRepository.deleteById(id);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (Exception var4) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping({"/products"})
    public ResponseEntity<HttpStatus> deleteAllProducts() {
        try {
            this.productRepository.deleteAll();
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (Exception var2) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}