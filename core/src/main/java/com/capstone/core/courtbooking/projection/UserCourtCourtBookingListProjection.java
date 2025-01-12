package com.capstone.core.courtbooking.projection;

import java.time.LocalTime;

public interface UserCourtCourtBookingListProjection {
    Long getId();
    LocalTime getUsageTimeStart();
    LocalTime getUsageTimeEnd();
    Long getStatus();
    Long getUserId();
}
