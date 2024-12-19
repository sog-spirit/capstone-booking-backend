package com.capstone.core.courtbooking.projection;

public interface CenterOwnerCourtBookingCenterListProjection {
    Center getCenter();

    interface Center {
        Long getId();
        String getName();
    }
}
