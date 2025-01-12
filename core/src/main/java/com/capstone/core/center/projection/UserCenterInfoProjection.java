package com.capstone.core.center.projection;

import java.time.LocalTime;

public interface UserCenterInfoProjection {
    LocalTime getOpeningTime();
    LocalTime getClosingTime();
    String getName();
    Long getCourtFee();
}
