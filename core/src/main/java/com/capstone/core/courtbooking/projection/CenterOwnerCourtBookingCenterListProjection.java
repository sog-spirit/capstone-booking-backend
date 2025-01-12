package com.capstone.core.courtbooking.projection;

public interface CenterOwnerCourtBookingCenterListProjection {
    Court getCourt();

    interface Court {
        Center getCenter();
    }

    interface Center {
        Long getId();
        String getName();
    }
}
