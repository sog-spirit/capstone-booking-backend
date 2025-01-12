package com.capstone.core.center.projection;

import java.time.LocalTime;

public interface UserCenterListProjection {
    Long getId();
    String getName();
    String getAddress();
    Long getCourtFee();
    LocalTime getOpeningTime();
    LocalTime getClosingTime();
    Long getReviewCount();
    Long getRecommendCount();
    Long getNotRecommendCount();
}
