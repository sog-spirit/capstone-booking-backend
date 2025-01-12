package com.capstone.core.courtbookingproductorder.projection;

import java.time.LocalDateTime;

public interface CenterOwnerCourtBookingProductOrderListProjection {
    Long getId();
    CourtBooking getCourtBooking();
    ProductInventory getProductInventory();
    User getUser();
    LocalDateTime getCreateTimestamp();
    Long getQuantity();
    Long getFee();
    Long getStatus();

    interface CourtBooking {
        Long getId();
    }

    interface ProductInventory {
        Long getId();
    }

    interface User {
        String getUsername();
    }
}
