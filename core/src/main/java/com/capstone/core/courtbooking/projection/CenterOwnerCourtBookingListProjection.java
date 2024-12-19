package com.capstone.core.courtbooking.projection;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public interface CenterOwnerCourtBookingListProjection {
    Long getId();
    LocalDateTime getCreateTimestamp();
    Center getCenter();
    Court getCourt();
    User getUser();
    LocalDate getUsageDate();
    LocalTime getUsageTimeStart();
    LocalTime getUsageTimeEnd();
    Status getStatus();

    interface Center {
        String getName();
    }

    interface Court {
        String getName();
    }

    interface User {
        String getUsername();
    }

    interface Status {
        String getName();
    }
}
