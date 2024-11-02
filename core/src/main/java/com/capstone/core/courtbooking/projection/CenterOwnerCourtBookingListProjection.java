package com.capstone.core.courtbooking.projection;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public interface CenterOwnerCourtBookingListProjection {
    Long getId();
    LocalDateTime getCreateTimestamp();
    String getCenterName();
    String getCourtName();
    String getUserUsername();
    LocalDate getUsageDate();
    LocalTime getUsageTimeStart();
    LocalTime getUsageTimeEnd();
    String getStatusName();
}
