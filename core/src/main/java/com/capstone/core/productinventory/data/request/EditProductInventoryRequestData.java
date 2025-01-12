package com.capstone.core.productinventory.data.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditProductInventoryRequestData {
    private Long id;
    private Long quantity;
}
