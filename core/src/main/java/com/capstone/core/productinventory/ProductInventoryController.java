package com.capstone.core.productinventory;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.core.productinventory.data.request.AddNewProductInventoryRequestData;
import com.capstone.core.productinventory.data.request.CenterOwnerProductInventoryManagementRequestData;
import com.capstone.core.productinventory.data.request.UserProductInventoryListRequestData;
import com.capstone.core.productinventory.projection.CenterOwnerProductInventoryListProjection;

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

    @GetMapping(value = "/user/list")
    ResponseEntity<Object> getUserProductInventoryList(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            UserProductInventoryListRequestData requestData) {
        return productInventoryService.getUserProductInventoryList(jwtToken, requestData);
    }

    @GetMapping(value = "/center-owner/list")
    ResponseEntity<Object> getCenterOwnerProductInventoryList(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            CenterOwnerProductInventoryManagementRequestData requestData) {
        return productInventoryService.getCenterOwnerProductInventoryList(jwtToken, requestData);
    }
}
