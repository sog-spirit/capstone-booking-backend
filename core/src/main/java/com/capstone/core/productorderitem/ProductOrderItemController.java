package com.capstone.core.productorderitem;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/product-order-item")
@AllArgsConstructor
public class ProductOrderItemController {
    private ProductOrderItemService productOrderItemService;
}
