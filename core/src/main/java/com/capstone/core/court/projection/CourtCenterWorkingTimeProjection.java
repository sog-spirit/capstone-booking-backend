package com.capstone.core.court.projection;

import java.time.LocalTime;

public interface CourtCenterWorkingTimeProjection {
    Center getCenter();

    interface Center {
        LocalTime getOpeningTime();
        LocalTime getClosingTime();
    }
}
