package com.capstone.core.courtbooking.projection;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public interface UserCourtBookingListProjection {
    Long getId();
    Court getCourt();
    Long getStatus();
    LocalDateTime getCreateTimestamp();
    LocalDate getUsageDate();
    LocalTime getUsageTimeStart();
    LocalTime getUsageTimeEnd();

    interface Center {
        String getName();
    }

    interface Court {
        String getName();
        Center getCenter();
    }
}
