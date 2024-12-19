package com.capstone.core.courtbooking.projection;

public interface UserCourtBookingCenterListProjection {
    Center getCenter();

    interface Center {
        Long getId();
        String getName();
    }
}
