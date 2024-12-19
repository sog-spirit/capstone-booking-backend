package com.capstone.core.centerreview.projection;

public interface CenterOwnerCenterReviewUserFilterListProjection {
    User getUser();

    interface User {
        Long getId();
        String getUsername();
    }
}
