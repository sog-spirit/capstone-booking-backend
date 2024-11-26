package com.capstone.core.productinventory.data.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddNewProductInventoryRequestData {
    private Long productId;
    private Long centerId;
    private Long quantity;
}
