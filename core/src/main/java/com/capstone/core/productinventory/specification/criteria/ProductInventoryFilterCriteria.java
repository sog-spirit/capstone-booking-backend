package com.capstone.core.productinventory.specification.criteria;

import lombok.Data;

@Data
public class ProductInventoryFilterCriteria {
    private Long userId;
    private Long centerId;
    private Long productId;
}
