package com.capstone.core.productinventory.specification.criteria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductInventoryFilterCriteria {
    private Long userId;
    private Long centerId;
    private Long productId;
}
