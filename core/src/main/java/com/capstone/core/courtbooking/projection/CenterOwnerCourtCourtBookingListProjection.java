package com.capstone.core.courtbooking.projection;

import java.time.LocalTime;

public interface CenterOwnerCourtCourtBookingListProjection {
    Long getId();
    LocalTime getUsageTimeStart();
    LocalTime getUsageTimeEnd();
}
