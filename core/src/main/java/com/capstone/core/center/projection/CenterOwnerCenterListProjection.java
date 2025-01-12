package com.capstone.core.center.projection;

import java.time.LocalTime;

public interface CenterOwnerCenterListProjection {
    Long getId();
    String getName();
    String getAddress();
    Long getCourtFee();
    LocalTime getOpeningTime();
    LocalTime getClosingTime();
}
