package com.capstone.core.courtbooking.projection;

public interface UserCourtBookingCenterListProjection {
    Court getCourt();

    interface Court {
        Center getCenter();
    }

    interface Center {
        Long getId();
        String getName();
    }
}
