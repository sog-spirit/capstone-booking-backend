package com.capstone.core.center.projection;

import java.time.LocalTime;

public interface CenterOwnerCenterInfoProjection {
    LocalTime getOpeningTime();
    LocalTime getClosingTime();
    String getName();
}
