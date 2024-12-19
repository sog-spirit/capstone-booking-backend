package com.capstone.core.courtbooking.projection;

public interface CenterOwnerCourtBookingUserListProjection {
    User getUser();

    interface User {
        Long getId();
        String getUsername();
    }
}
