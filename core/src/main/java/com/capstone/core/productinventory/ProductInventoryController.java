package com.capstone.core.productinventory;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.core.productinventory.data.AddNewProductInventoryRequestData;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/product-inventory")
@AllArgsConstructor
public class ProductInventoryController {
    private ProductInventoryService productInventoryService;

    @PostMapping
    @Transactional
    ResponseEntity<Object> addProductInventory(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            @RequestBody @Valid AddNewProductInventoryRequestData addNewProductInventoryRequestData) {
        return productInventoryService.addProductInventory(jwtToken, addNewProductInventoryRequestData);
    }
}
