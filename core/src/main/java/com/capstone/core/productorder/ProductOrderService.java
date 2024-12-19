package com.capstone.core.productorder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.capstone.core.productinventory.ProductInventoryRepository;
import com.capstone.core.productorder.data.request.CenterOwnerProductOrderCenterFilterListRequestData;
import com.capstone.core.productorder.data.request.CenterOwnerProductOrderListRequestData;
import com.capstone.core.productorder.data.request.CenterOwnerProductOrderUserFilterListRequestData;
import com.capstone.core.productorder.data.request.ProductOrderRequestData;
import com.capstone.core.productorder.data.request.ProductOrderRequestData.CartItem;
import com.capstone.core.productorder.data.response.CenterOwnerProductOrderListResponseData;
import com.capstone.core.productorder.projection.CenterOwnerProductOrderCenterFilterListProjection;
import com.capstone.core.productorder.projection.CenterOwnerProductOrderListProjection;
import com.capstone.core.productorder.projection.CenterOwnerProductOrderUserFilterListProjection;
import com.capstone.core.productorder.specification.ProductOrderSpecification;
import com.capstone.core.productorder.specification.criteria.ProductOrderCriteria;
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

    ResponseEntity<Object> getCenterOwnerProductOrderList(String jwtToken, CenterOwnerProductOrderListRequestData requestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        ProductOrderCriteria productOrderCriteria = new ProductOrderCriteria();
        productOrderCriteria.setCenterUserId(userId);
        productOrderCriteria.setId(requestData.getId());
        productOrderCriteria.setUserId(requestData.getUserId());
        productOrderCriteria.setCreateTimestampFrom(requestData.getCreateTimestampFrom());
        productOrderCriteria.setCreateTimestampTo(requestData.getCreateTimestampTo());
        productOrderCriteria.setTotalFrom(requestData.getTotalFrom());
        productOrderCriteria.setTotalTo(requestData.getTotalTo());
        productOrderCriteria.setCenterId(requestData.getCenterId());
        productOrderCriteria.setStatusId(requestData.getStatusId());

        List<Sort.Order> sortOrders = new ArrayList<>();
        if (requestData.getIdSortOrder() != null) {
            sortOrders.add(new Sort.Order(requestData.getIdSortOrder().equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, "id"));
        }
        if (requestData.getUserSortOrder() != null) {
            sortOrders.add(new Sort.Order(requestData.getUserSortOrder().equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, "user"));
        }
        if (requestData.getCreateTimestampSortOrder() != null) {
            sortOrders.add(new Sort.Order(requestData.getCreateTimestampSortOrder().equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, "createTimestamp"));
        }
        if (requestData.getTotalSortOrder() != null) {
            sortOrders.add(new Sort.Order(requestData.getTotalSortOrder().equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, "total"));
        }
        if (requestData.getCenterSortOrder() != null) {
            sortOrders.add(new Sort.Order(requestData.getCenterSortOrder().equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, "center"));
        }
        if (requestData.getStatusSortOrder() != null) {
            sortOrders.add(new Sort.Order(requestData.getStatusSortOrder().equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, "status"));
        }

        Sort sort = Sort.by(sortOrders);

        Pageable pageable = PageRequest.of(requestData.getPageNo(), requestData.getPageSize(), sort);

        Page<CenterOwnerProductOrderListProjection> productOrderList = productOrderRepository.findBy(new ProductOrderSpecification(productOrderCriteria), q -> q.as(CenterOwnerProductOrderListProjection.class).sortBy(pageable.getSort()).page(pageable));
        CenterOwnerProductOrderListResponseData responseData = new CenterOwnerProductOrderListResponseData();
        responseData.setTotalPage(productOrderList.getTotalPages());
        responseData.setProductOrderList(productOrderList.getContent());

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    ResponseEntity<Object> getCenterOwnerProductOrderUserFilterList(String jwtToken, CenterOwnerProductOrderUserFilterListRequestData requestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<CenterOwnerProductOrderUserFilterListProjection> userList = productOrderRepository.findCenterOwnerProductOrderUserFilterListDistinctUserIdByCenterUserIdAndUserUsernameContaining(userId, requestData.getQuery());
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    ResponseEntity<Object> getCenterOwnerProductOrderCenterFilterList(String jwtToken, CenterOwnerProductOrderCenterFilterListRequestData requestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<CenterOwnerProductOrderCenterFilterListProjection> centerList = productOrderRepository.findCenterOwnerProductOrderCenterFilterListDistinctCenterIdByCenterUserIdAndCenterNameContaining(userId, requestData.getQuery());
        return new ResponseEntity<>(centerList, HttpStatus.OK);
    }
}
