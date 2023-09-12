package com.infy.EcommerceApp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.infy.EcommerceApp.assemblers.ProductModelAssembler;
import com.infy.EcommerceApp.enums.ProductCategory;
import com.infy.EcommerceApp.enums.ProductManufacturer;
import com.infy.EcommerceApp.exceptions.ProductNotFoundException;
import com.infy.EcommerceApp.model.Product;
import com.infy.EcommerceApp.model.Product;
import com.infy.EcommerceApp.repository.ProductRepository;
import lombok.SneakyThrows;
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

/**
 * Controller class responsible for managing product-related endpoints.
 */
@RestController
public class ProductController {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductModelAssembler productModelAssembler;

    /**
     * Default constructor for ProductController.
     */
    public ProductController() {
    }

    /**
     * Retrieves all products and returns them as a ResponseEntity with HATEOAS links.
     *
     * @return A ResponseEntity containing a collection of EntityModel instances representing products.
     */
    @GetMapping("/products")
    public ResponseEntity<CollectionModel<EntityModel<Product>>> getAllProducts() {
        List<EntityModel<Product>> products = productRepository.findAll().stream()
                .map(product -> EntityModel.of(product,
                        linkTo(methodOn(ProductController.class).getProductById(product.getProductId())).withSelfRel()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(products, linkTo(methodOn(ProductController.class).getAllProducts()).withSelfRel()));
    }

    /**
     * Retrieves a product by its ID and returns it as a ResponseEntity with HATEOAS links.
     *
     * @param id The ID of the product to retrieve.
     * @return A ResponseEntity containing an EntityModel representing the product.
     * @throws ProductNotFoundException If the product with the given ID is not found.
     */
    @SneakyThrows
    @GetMapping("/products/{id}")
    public ResponseEntity<EntityModel<Product>> getProductById(@PathVariable("id") Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        return ResponseEntity.ok(productModelAssembler.toModel(product));
    }
    /**
     * Retrieves products by category and returns them as a ResponseEntity with HATEOAS links.
     *
     * @param category The category of products to retrieve.
     * @return A ResponseEntity containing a collection of EntityModel instances representing products within the specified category.
     */
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
    /**
     * Retrieves products by name containing the specified keyword and returns them as a ResponseEntity with HATEOAS links.
     *
     * @param name The keyword to search for in product names.
     * @return A ResponseEntity containing a collection of EntityModel instances representing products matching the name keyword.
     */
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
    /**
     * Retrieves products by manufacturer and returns them as a ResponseEntity with HATEOAS links.
     *
     * @param manufacturer The manufacturer of products to retrieve.
     * @return A ResponseEntity containing a collection of EntityModel instances representing products from the specified manufacturer.
     */
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
    /**
     * Creates a new product and returns it as a ResponseEntity with a link to its resource.
     *
     * @param product The product to create.
     * @return A ResponseEntity containing the created product and a link to its resource.
     */
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
    /**
     * Updates an existing product with the provided data.
     *
     * @param id      The ID of the product to update.
     * @param product The updated product data.
     * @return A ResponseEntity containing the updated product if successful, or a not-found response if the product does not exist.
     */
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
    /**
     * Deletes a product by its ID.
     *
     * @param id The ID of the product to delete.
     * @return A ResponseEntity with a success status if the product is deleted, or an internal server error if an exception occurs.
     */
    @DeleteMapping("/products/{id}")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable("id") long id) {
        try {
            productRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    /**
     * Deletes all products.
     *
     * @return A ResponseEntity with a success status if all products are deleted, or an internal server error if an exception occurs.
     */
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
