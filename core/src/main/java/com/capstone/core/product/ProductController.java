package com.capstone.core.product;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.core.product.data.CreateProductRequestData;
import com.capstone.core.product.data.EditProductRequestData;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/product")
@AllArgsConstructor
public class ProductController {

    private ProductService productService;

    @PostMapping
    @Transactional
    ResponseEntity<Object> createProduct(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            @RequestBody @Valid CreateProductRequestData createCustomerServiceRequestData) {
        return productService.createProduct(jwtToken, createCustomerServiceRequestData);
    }

    @PutMapping
    @Transactional
    ResponseEntity<Object> editProduct(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            @RequestBody @Valid EditProductRequestData editProductRequestData) {
        return productService.editProduct(jwtToken, editProductRequestData);
    }

    @GetMapping(value = "/list")
    ResponseEntity<Object> getProductList(@RequestHeader(name = "Authorization", required = true) String jwtToken) {
        return productService.getProductList(jwtToken);
    }

    @GetMapping(value = "/list", params = {"query"})
    ResponseEntity<Object> getProductListDropdown(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            @RequestParam String query) {
        return productService.getProductListDropdown(jwtToken, query);
    }
}
