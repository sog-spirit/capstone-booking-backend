package com.capstone.core.productorder;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.core.productorder.data.request.CenterOwnerProductOrderCenterFilterListRequestData;
import com.capstone.core.productorder.data.request.CenterOwnerProductOrderListRequestData;
import com.capstone.core.productorder.data.request.CenterOwnerProductOrderUserFilterListRequestData;
import com.capstone.core.productorder.data.request.ProductOrderRequestData;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/product-order")
@AllArgsConstructor
public class ProductOrderController {
    private ProductOrderService productOrderService;

    @PostMapping
    @Transactional
    ResponseEntity<Object> addNewProductOrder(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            @RequestBody @Valid ProductOrderRequestData productOrderRequestData) {
        return productOrderService.addNewProductOrder(jwtToken, productOrderRequestData);
    }

    @GetMapping(value = "/center-owner/list")
    ResponseEntity<Object> getCenterOwnerProductOrderList(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            CenterOwnerProductOrderListRequestData requestData) {
        return productOrderService.getCenterOwnerProductOrderList(jwtToken, requestData);
    }

    @GetMapping(value = "/center-owner/user/filter/list")
    ResponseEntity<Object> getCenterOwnerProductOrderUserFilterList(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            CenterOwnerProductOrderUserFilterListRequestData requestData) {
        return productOrderService.getCenterOwnerProductOrderUserFilterList(jwtToken, requestData);
    }

    @GetMapping(value = "/center-owner/center/filter/list")
    ResponseEntity<Object> getCenterOwnerProductOrderCenterFilterList(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            CenterOwnerProductOrderCenterFilterListRequestData requestData) {
        return productOrderService.getCenterOwnerProductOrderCenterFilterList(jwtToken, requestData);
    }
}
