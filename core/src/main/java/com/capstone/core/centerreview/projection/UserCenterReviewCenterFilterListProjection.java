package com.capstone.core.centerreview.projection;

public interface UserCenterReviewCenterFilterListProjection {
    Center getCenter();

    interface Center {
        Long getId();
        String getName();
    }
}
