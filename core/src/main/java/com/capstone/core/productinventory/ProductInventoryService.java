package com.capstone.core.productinventory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.capstone.core.productinventory.data.AddNewProductInventoryRequestData;
import com.capstone.core.table.CenterTable;
import com.capstone.core.table.ProductInventoryTable;
import com.capstone.core.table.ProductTable;
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

        productInventoryRepository.save(newProductInventory);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
