package com.capstone.core.courtbooking.projection;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public interface UserCourtBookingListProjection {
    Long getId();
    String getCenterName();
    String getCourtName();
    LocalDateTime getCreateTimestamp();
    LocalDate getUsageDate();
    LocalTime getUsageTimeStart();
    LocalTime getUsageTimeEnd();
    String getStatusName();
}
