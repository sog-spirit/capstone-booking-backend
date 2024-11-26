package com.capstone.core.product;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.core.product.data.request.CenterOwnerProductListRequestData;
import com.capstone.core.product.data.request.CreateProductRequestData;
import com.capstone.core.product.data.request.EditProductRequestData;
import com.capstone.core.product.data.request.ProductListDropdownRequestData;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/product")
@AllArgsConstructor
public class ProductController {

    private ProductService productService;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    ResponseEntity<Object> createProduct(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            @ModelAttribute @Valid CreateProductRequestData createCustomerServiceRequestData) throws IOException {
        return productService.createProduct(jwtToken, createCustomerServiceRequestData);
    }

    @PutMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    ResponseEntity<Object> editProduct(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            @ModelAttribute @Valid EditProductRequestData editProductRequestData) throws IOException {
        return productService.editProduct(jwtToken, editProductRequestData);
    }

    @GetMapping(value = "/center-owner/dropdown/list", params = {"query"})
    ResponseEntity<Object> getProductListDropdown(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            ProductListDropdownRequestData requestData) {
        return productService.getProductListDropdown(jwtToken, requestData);
    }

    @GetMapping("/center-owner/list")
    ResponseEntity<Object> getCenterOwnerProductList(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            CenterOwnerProductListRequestData requestData) {
        return productService.getCenterOwnerProductList(jwtToken, requestData);
    }
}
