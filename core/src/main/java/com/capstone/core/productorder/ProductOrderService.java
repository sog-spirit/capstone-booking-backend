package com.capstone.core.productorder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.capstone.core.productinventory.ProductInventoryRepository;
import com.capstone.core.productorder.data.ProductOrderRequestData;
import com.capstone.core.productorder.data.ProductOrderRequestData.CartItem;
import com.capstone.core.productorder.projection.CenterOwnerProductOrderListProjection;
import com.capstone.core.productorderitem.ProductOrderItemRepository;
import com.capstone.core.table.CenterTable;
import com.capstone.core.table.ProductInventoryTable;
import com.capstone.core.table.ProductOrderItemTable;
import com.capstone.core.table.ProductOrderStatusTable;
import com.capstone.core.table.ProductOrderTable;
import com.capstone.core.table.UserTable;
import com.capstone.core.util.consts.ProductOrderStatus;
import com.capstone.core.util.security.jwt.JwtUtil;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductOrderService {
    private final ProductOrderRepository productOrderRepository;
    private final ProductOrderItemRepository productOrderItemRepository;
    private final ProductInventoryRepository productInventoryRepository;

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
        List<ProductInventoryTable> productInventoryItems = new ArrayList<>();
        ProductOrderItemTable productOrderItem;
        ProductInventoryTable productInventoryItem;
        for (CartItem cartItem : productOrderRequestData.getCart()) {
            productInventoryItem = productInventoryRepository.getReferenceById(cartItem.getProductInventoryId());
            productInventoryItem.setQuantity(productInventoryItem.getQuantity() - cartItem.getQuantity());
            productInventoryItems.add(productInventoryItem);

            productOrderItem = new ProductOrderItemTable();
            productOrderItem.setOrder(savedProductOrder);
            productOrderItem.setProductInventory(productInventoryItem);
            productOrderItem.setQuantity(cartItem.getQuantity());
            productOrderItems.add(productOrderItem);
        }
        productOrderItemRepository.saveAll(productOrderItems);
        productInventoryRepository.saveAll(productInventoryItems);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    ResponseEntity<Object> getCenterOwnerProductOrderList(String jwtToken) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<CenterOwnerProductOrderListProjection> productOrderList = productOrderRepository.findCenterOwnerProductOrderListByUserId(userId);
        return new ResponseEntity<>(productOrderList, HttpStatus.OK);
    }
}
