package com.capstone.core.productorderstatus;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/product-order-status")
@AllArgsConstructor
public class ProductOrderStatusController {
    private ProductOrderStatusService productOrderStatusService;
}
