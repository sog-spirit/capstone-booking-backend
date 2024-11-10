package com.capstone.core.productinventory;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.capstone.core.productinventory.data.AddNewProductInventoryRequestData;
import com.capstone.core.productinventory.projection.ProductInventoryListProjection;
import com.capstone.core.productinventory.projection.UserProductOrderPageListProjection;
import com.capstone.core.table.CenterTable;
import com.capstone.core.table.ProductInventoryTable;
import com.capstone.core.table.ProductTable;
import com.capstone.core.table.UserTable;
import com.capstone.core.util.security.jwt.JwtUtil;

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

        List<ProductInventoryListProjection> productInventoryList = productInventoryRepository.findByUserId(userId);
        return new ResponseEntity<>(productInventoryList, HttpStatus.OK);
    }

    ResponseEntity<Object> getUserProductInventoryList(String jwtToken, Long centerId) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<UserProductOrderPageListProjection> productList = productInventoryRepository.findUserProductOrderPageListByCenterId(centerId);
        return new ResponseEntity<>(productList, HttpStatus.OK); 
    }
}
