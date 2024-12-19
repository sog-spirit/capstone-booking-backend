package com.capstone.core.productinventory.projection;

public interface CenterOwnerProductInventoryProductFilterListInterface {
    Product getProduct();

    interface Product {
        Long getId();
        String getName();
    }
}
