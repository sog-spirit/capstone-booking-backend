package com.capstone.core.courtbookingproductorder.projection;

import java.time.LocalDateTime;

public interface UserCourtBookingProductOrderDetailListProjection {
    Long getId();
    Long getProductInventoryProductId();
    Long getFee();
    Long getQuantity();
    LocalDateTime getCreateTimestamp();
    Long getStatus();
}
