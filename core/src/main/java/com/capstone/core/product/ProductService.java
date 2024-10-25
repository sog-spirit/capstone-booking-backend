package com.capstone.core.product;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.capstone.core.product.data.CreateProductRequestData;
import com.capstone.core.table.ProductTable;
import com.capstone.core.table.UserTable;
import com.capstone.core.util.security.jwt.JwtUtil;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductService {

    private ProductRepository customerServiceRepository;

    ResponseEntity<Object> createProduct(String jwtToken, CreateProductRequestData createCustomerServiceRequestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        ProductTable newProduct = new ProductTable();
        newProduct.setName(createCustomerServiceRequestData.getName());
        newProduct.setPrice(createCustomerServiceRequestData.getPrice());
        UserTable user = new UserTable();
        user.setId(userId);
        newProduct.setUser(user);
        customerServiceRepository.save(newProduct);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
