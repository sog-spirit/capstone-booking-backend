package com.capstone.core.productorderstatus;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.capstone.core.productorderstatus.projection.ProductOrderStatusProjection;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductOrderStatusService {
    private ProductOrderStatusRepository productOrderStatusRepository;

    ResponseEntity<Object> getProductOrderStatus() {
        List<ProductOrderStatusProjection> statusList = productOrderStatusRepository.findProductOrderStatusBy(); 
        return new ResponseEntity<>(statusList, HttpStatus.OK);
    }
}
