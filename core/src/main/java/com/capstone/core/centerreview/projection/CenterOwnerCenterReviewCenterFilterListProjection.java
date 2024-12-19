package com.capstone.core.centerreview.projection;

public interface CenterOwnerCenterReviewCenterFilterListProjection {
    Center getCenter();

    interface Center {
        Long getId();
        String getName();
    }
}
