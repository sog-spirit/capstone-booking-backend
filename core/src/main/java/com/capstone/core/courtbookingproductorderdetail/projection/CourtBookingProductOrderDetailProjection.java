package com.capstone.core.courtbookingproductorderdetail.projection;

public interface CourtBookingProductOrderDetailProjection {
    Long getId();
    Long getQuantity();
    Long getFee();

    Long getProductInventoryProductId();
    String getProductInventoryProductName();
}
