package com.capstone.core.courtbookingproductorder.projection;

import java.time.LocalDateTime;

public interface UserCourtBookingProductOrderDetailListProjection {
    Long getId();
    Long getFee();
    LocalDateTime getCreateTimestamp();
    Long getStatus();
}
