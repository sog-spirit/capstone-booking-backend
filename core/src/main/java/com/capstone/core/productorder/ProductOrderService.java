package com.capstone.core.productorder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.capstone.core.productorder.data.ProductOrderRequestData;
import com.capstone.core.productorder.data.ProductOrderRequestData.CartItem;
import com.capstone.core.productorderitem.ProductOrderItemRepository;
import com.capstone.core.table.CenterTable;
import com.capstone.core.table.ProductOrderItemTable;
import com.capstone.core.table.ProductOrderStatusTable;
import com.capstone.core.table.ProductOrderTable;
import com.capstone.core.table.ProductTable;
import com.capstone.core.table.UserTable;
import com.capstone.core.util.consts.ProductOrderStatus;
import com.capstone.core.util.security.jwt.JwtUtil;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductOrderService {
    private ProductOrderRepository productOrderRepository;
    private ProductOrderItemRepository productOrderItemRepository;

    @Transactional
    ResponseEntity<Object> addNewProductOrder(String jwtToken, ProductOrderRequestData productOrderRequestData) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        ProductOrderTable productOrder = new ProductOrderTable();
        UserTable user = new UserTable();
        user.setId(userId);
        productOrder.setUser(user);
        productOrder.setCreateTimestamp(currentDateTime);
        productOrder.setTotal(productOrderRequestData.getTotal());

        CenterTable center= new CenterTable();
        center.setId(productOrderRequestData.getCenterId());
        productOrder.setCenter(center);

        ProductOrderStatusTable productOrderStatus = new ProductOrderStatusTable();
        productOrderStatus.setId(ProductOrderStatus.PENDING.getValue());
        productOrder.setStatus(productOrderStatus);

        ProductOrderTable savedProductOrder = productOrderRepository.save(productOrder);
        List<ProductOrderItemTable> productOrderItems = new ArrayList<>();
        ProductOrderItemTable productOrderItem;
        ProductTable product;
        for (CartItem cartItem : productOrderRequestData.getCart()) {
            productOrderItem = new ProductOrderItemTable();
            productOrderItem.setOrder(savedProductOrder);
            product = new ProductTable();
            product.setId(cartItem.getProductId());
            productOrderItem.setProduct(product);
            productOrderItem.setQuantity(cartItem.getQuantity());
            productOrderItems.add(productOrderItem);
        }
        productOrderItemRepository.saveAll(productOrderItems);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
