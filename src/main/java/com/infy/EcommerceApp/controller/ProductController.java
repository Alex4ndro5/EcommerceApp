package com.infy.EcommerceApp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.infy.EcommerceApp.assemblers.ProductModelAssembler;
import com.infy.EcommerceApp.enums.ProductCategory;
import com.infy.EcommerceApp.enums.ProductManufacturer;
import com.infy.EcommerceApp.model.Product;
import com.infy.EcommerceApp.repository.ProductRepository;
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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class ProductController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductModelAssembler productModelAssembler;

    public ProductController() {
    }

    @GetMapping("/products")
    public ResponseEntity<CollectionModel<EntityModel<Product>>> getAllProducts() {
        List<EntityModel<Product>> products = productRepository.findAll().stream()
                .map(product -> EntityModel.of(product,
                        linkTo(methodOn(ProductController.class).getProductById(product.getProductId())).withSelfRel()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(products, linkTo(methodOn(ProductController.class).getAllProducts()).withSelfRel()));
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<EntityModel<Product>> getProductById(@PathVariable("id") Long id) {
        Optional<Product> productData = productRepository.findById(id);
        if (productData.isPresent()) {
            return ResponseEntity.ok(EntityModel.of(productData.get(),
                    linkTo(methodOn(ProductController.class).getProductById(id)).withSelfRel()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/products/category/{category}")
    public ResponseEntity<CollectionModel<EntityModel<Product>>> getProductsByCategory(@PathVariable("category") String category) {
        try {
            ProductCategory productCategory = ProductCategory.valueOf(category.toUpperCase());

            List<EntityModel<Product>> products = productRepository.findByProductCategory(productCategory).stream()
                    .map(product -> EntityModel.of(product,
                            linkTo(methodOn(ProductController.class).getProductById(product.getProductId())).withSelfRel()))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(CollectionModel.of(products, linkTo(methodOn(ProductController.class).getProductsByCategory(category)).withSelfRel()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/products/name/{name}")
    public ResponseEntity<CollectionModel<EntityModel<Product>>> getProductsByName(@PathVariable("name") String name) {
        try {
            List<EntityModel<Product>> products = productRepository.findByProductNameContaining(name).stream()
                    .map(product -> EntityModel.of(product,
                            linkTo(methodOn(ProductController.class).getProductById(product.getProductId())).withSelfRel()))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(CollectionModel.of(products, linkTo(methodOn(ProductController.class).getProductsByName(name)).withSelfRel()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/products/manufacturer/{manufacturer}")
    public ResponseEntity<CollectionModel<EntityModel<Product>>> getProductsByManufacturer(@PathVariable("manufacturer") String manufacturer) {
        try {
            ProductManufacturer productManufacturer = ProductManufacturer.valueOf(manufacturer.toUpperCase());

            List<EntityModel<Product>> products = productRepository.findByProductManufacturer(productManufacturer).stream()
                    .map(product -> EntityModel.of(product,
                            linkTo(methodOn(ProductController.class).getProductById(product.getProductId())).withSelfRel()))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(CollectionModel.of(products, linkTo(methodOn(ProductController.class).getProductsByManufacturer(manufacturer)).withSelfRel()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        try {
            Product newProduct = productRepository.save(product);
            return ResponseEntity.created(linkTo(methodOn(ProductController.class).getProductById(newProduct.getProductId())).toUri())
                    .body(newProduct);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Long id, @RequestBody Product product) {
        Optional<Product> productData = productRepository.findById(id);
        if (productData.isPresent()) {
            Product existingProduct = productData.get();
            existingProduct.setProductPrice(product.getProductPrice());
            existingProduct.setProductName(product.getProductName());
            existingProduct.setProductCategory(product.getProductCategory());
            existingProduct.setProductManufacturer(product.getProductManufacturer());
            existingProduct.setProductPictureUrl(product.getProductPictureUrl());

            return ResponseEntity.ok(productRepository.save(existingProduct));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable("id") long id) {
        try {
            productRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/products")
    public ResponseEntity<HttpStatus> deleteAllProducts() {
        try {
            productRepository.deleteAll();
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
