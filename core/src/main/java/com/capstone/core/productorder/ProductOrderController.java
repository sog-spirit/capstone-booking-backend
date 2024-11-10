package com.capstone.core.productorder;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.core.productorder.data.ProductOrderRequestData;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/product-order")
@AllArgsConstructor
public class ProductOrderController {
    private ProductOrderService productOrderService;

    @PostMapping
    ResponseEntity<Object> addNewProductOrder(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            @RequestBody @Valid ProductOrderRequestData productOrderRequestData) {
        return productOrderService.addNewProductOrder(jwtToken, productOrderRequestData);
    }
}
