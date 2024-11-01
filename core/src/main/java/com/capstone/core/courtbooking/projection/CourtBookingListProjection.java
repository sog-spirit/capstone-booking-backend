package com.capstone.core.courtbooking.projection;

import java.time.LocalTime;

public interface CourtBookingListProjection {
    Long getId();
    LocalTime getUsageTimeStart();
    LocalTime getUsageTimeEnd();
}
