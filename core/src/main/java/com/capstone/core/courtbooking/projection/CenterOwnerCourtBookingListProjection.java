package com.capstone.core.courtbooking.projection;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public interface CenterOwnerCourtBookingListProjection {
    Long getId();
    LocalDateTime getCreateTimestamp();
    Court getCourt();
    User getUser();
    LocalDate getUsageDate();
    LocalTime getUsageTimeStart();
    LocalTime getUsageTimeEnd();
    Long getStatus();

    interface Center {
        String getName();
    }

    interface Court {
        String getName();
        Center getCenter();
    }

    interface User {
        String getUsername();
    }
}
