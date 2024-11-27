package com.capstone.core.center.projection;

import java.time.LocalTime;

public interface CenterOwnerCenterWorkingTimeProjection {
    LocalTime getOpeningTime();
    LocalTime getClosingTime();
}
