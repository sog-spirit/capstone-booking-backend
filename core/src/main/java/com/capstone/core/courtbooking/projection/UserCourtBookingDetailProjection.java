package com.capstone.core.courtbooking.projection;

import java.time.LocalDateTime;
import java.time.LocalTime;

public interface UserCourtBookingDetailProjection {
    LocalDateTime getCreateTimestamp();
    Long getCourtFee();
    Long getProductFee();
    LocalTime getUsageTimeStart();
    LocalTime getUsageTimeEnd();
    Long getStatus();
}
