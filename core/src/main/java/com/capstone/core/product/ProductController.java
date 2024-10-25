package com.capstone.core.product;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.core.product.data.CreateProductRequestData;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/product")
@AllArgsConstructor
public class ProductController {

    private ProductService customerServiceService;

    @PostMapping
    ResponseEntity<Object> createProduct(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            @RequestBody @Valid CreateProductRequestData createCustomerServiceRequestData) {
        return customerServiceService.createProduct(jwtToken, createCustomerServiceRequestData);
    }
}
