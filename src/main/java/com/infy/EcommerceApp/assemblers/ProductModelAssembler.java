package com.infy.EcommerceApp.assemblers;


import com.infy.EcommerceApp.controller.ProductController;
import com.infy.EcommerceApp.model.Product;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * A component for assembling representations of Product entities into EntityModel instances.
 */
@Component
public class ProductModelAssembler implements RepresentationModelAssembler<Product, EntityModel<Product>> {

    /**
     * Converts a Product entity into an EntityModel representation with appropriate links.
     *
     * @param product The Product entity to be converted.
     * @return An EntityModel representation of the Product with self and related links.
     */
    @Override
    public EntityModel<Product> toModel(Product product) {
        return EntityModel.of(product, //
                linkTo(methodOn(ProductController.class).getProductById(product.getProductId())).withSelfRel(),
                linkTo(methodOn(ProductController.class).getAllProducts()).withRel("products"));
    }
}

