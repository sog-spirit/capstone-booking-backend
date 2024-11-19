package com.capstone.core.productinventory.projection;

public interface CenterOwnerProductInventoryListProjection {
    Long getId();
    Long getQuantity();
    Center getCenter();
    Product getProduct();

    interface Center {
        Long getId();
        String getName();
    }

    interface Product {
        Long getId();
        String getName();
    }
}