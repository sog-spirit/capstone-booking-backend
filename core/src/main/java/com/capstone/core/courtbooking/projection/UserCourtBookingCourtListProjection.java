package com.capstone.core.courtbooking.projection;

public interface UserCourtBookingCourtListProjection {
    Court getCourt();

    interface Court {
        Long getId();
        String getName();
    }
}
