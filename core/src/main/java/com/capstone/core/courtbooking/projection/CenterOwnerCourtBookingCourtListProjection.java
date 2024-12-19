package com.capstone.core.courtbooking.projection;

public interface CenterOwnerCourtBookingCourtListProjection {
    Court getCourt();

    interface Court {
        Long getId();
        String getName();
    }
}
