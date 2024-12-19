package com.capstone.core.productinventory;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.capstone.core.productinventory.data.request.AddNewProductInventoryRequestData;
import com.capstone.core.productinventory.data.request.CenterOwnerProductInventoryCenterFilterListRequestData;
import com.capstone.core.productinventory.data.request.CenterOwnerProductInventoryManagementRequestData;
import com.capstone.core.productinventory.data.request.CenterOwnerProductInventoryProductFilterListRequestData;
import com.capstone.core.productinventory.data.request.UserProductInventoryListRequestData;
import com.capstone.core.productinventory.data.response.CenterOwnerProductInventoryManagementResponseData;
import com.capstone.core.productinventory.projection.CenterOwnerProductInventoryCenterFilterListProjection;
import com.capstone.core.productinventory.projection.CenterOwnerProductInventoryListProjection;
import com.capstone.core.productinventory.projection.CenterOwnerProductInventoryProductFilterListInterface;
import com.capstone.core.productinventory.projection.UserProductOrderPageListProjection;
import com.capstone.core.productinventory.specification.ProductInventorySpecification;
import com.capstone.core.productinventory.specification.criteria.ProductInventoryFilterCriteria;
import com.capstone.core.table.CenterTable;
import com.capstone.core.table.ProductInventoryTable;
import com.capstone.core.table.ProductTable;
import com.capstone.core.table.UserTable;
import com.capstone.core.util.security.jwt.JwtUtil;

import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Order;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductInventoryService {
    private ProductInventoryRepository productInventoryRepository;
    
    ResponseEntity<Object> addProductInventory(String jwtToken, AddNewProductInventoryRequestData addNewProductInventoryRequestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        ProductInventoryTable newProductInventory = new ProductInventoryTable();
        newProductInventory.setQuantity(addNewProductInventoryRequestData.getQuantity());

        ProductTable product = new ProductTable();
        product.setId(addNewProductInventoryRequestData.getProductId());
        newProductInventory.setProduct(product);

        CenterTable center = new CenterTable();
        center.setId(addNewProductInventoryRequestData.getCenterId());
        newProductInventory.setCenter(center);

        UserTable user = new UserTable();
        user.setId(userId);
        newProductInventory.setUser(user);

        productInventoryRepository.save(newProductInventory);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    ResponseEntity<Object> getProductInventoryList(String jwtToken) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<CenterOwnerProductInventoryListProjection> productInventoryList = productInventoryRepository.findByUserId(userId);
        return new ResponseEntity<>(productInventoryList, HttpStatus.OK);
    }

    ResponseEntity<Object> getUserProductInventoryList(String jwtToken, UserProductInventoryListRequestData requestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<UserProductOrderPageListProjection> productList = productInventoryRepository.findUserProductOrderPageListByCenterId(requestData.getCenterId());
        return new ResponseEntity<>(productList, HttpStatus.OK); 
    }

    ResponseEntity<Object> getCenterOwnerProductInventoryList(String jwtToken, CenterOwnerProductInventoryManagementRequestData requestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        ProductInventoryFilterCriteria filterCriteria = new ProductInventoryFilterCriteria();
        filterCriteria.setUserId(userId);
        filterCriteria.setCenterId(requestData.getCenterIdFilter() != null ? Long.parseLong(requestData.getCenterIdFilter()) : null);
        filterCriteria.setProductId(requestData.getProductIdFilter() != null ? Long.parseLong(requestData.getProductIdFilter()) : null);

        List<Sort.Order> sortOrders = new ArrayList<>();
        if (requestData.getIdSortOrder() != null) {
            sortOrders.add(new Sort.Order(requestData.getIdSortOrder().equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, "id"));
        }
        if (requestData.getProductSortOrder() != null) {
            sortOrders.add(new Sort.Order(requestData.getProductSortOrder().equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, "product"));
        }
        if (requestData.getCenterSortOrder() != null) {
            sortOrders.add(new Sort.Order(requestData.getCenterSortOrder().equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, "center"));
        }
        if (requestData.getQuantitySortOrder() != null) {
            sortOrders.add(new Sort.Order(requestData.getQuantitySortOrder().equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, "quantity"));
        }
        Sort sort = Sort.by(sortOrders);

        Pageable pageable = PageRequest.of(requestData.getPageNo(), requestData.getPageSize(), sort);

        CenterOwnerProductInventoryManagementResponseData responseData = new CenterOwnerProductInventoryManagementResponseData();
        Page<CenterOwnerProductInventoryListProjection> productInventoryList = productInventoryRepository.findBy(new ProductInventorySpecification(filterCriteria), q -> q.as(CenterOwnerProductInventoryListProjection.class).sortBy(pageable.getSort()).page(pageable));
        responseData.setProductInventoryList(productInventoryList.getContent());
        responseData.setTotalPage(productInventoryList.getTotalPages());

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    ResponseEntity<Object> getCenterOwnerProductInventoryCenterFilterList(String jwtToken, CenterOwnerProductInventoryCenterFilterListRequestData requestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<CenterOwnerProductInventoryCenterFilterListProjection> centerList = productInventoryRepository.findCenterOwnerProductInventoryCenterFilterListDistinctCenterIdByUserIdAndCenterNameContaining(userId, requestData.getQuery());
        return new ResponseEntity<Object>(centerList, HttpStatus.OK);
    }

    ResponseEntity<Object> getCenterOwnerProductInventoryProductFilterList(String jwtToken, CenterOwnerProductInventoryProductFilterListRequestData requestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<CenterOwnerProductInventoryProductFilterListInterface> productList = productInventoryRepository.findCenterOwnerProductInventoryProductFilterListDistinctProductIdByUserIdAndProductNameContaining(userId, requestData.getQuery());
        return new ResponseEntity<Object>(productList, HttpStatus.OK);
    }
}
