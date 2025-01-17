package com.capstone.core.courtbookingproductorder.projection;

import java.time.LocalDateTime;

public interface CenterOwnerCourtBookingProductOrderDetailListProjection {
    Long getId();
    Long getFee();
    LocalDateTime getCreateTimestamp();
    Long getStatus();
}
