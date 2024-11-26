package com.capstone.core.centerreview.projection;

public interface UserCenterReviewListProjection {
    Long getId();
    String getContent();
    Center getCenter();

    interface Center {
        String getName();
    }
}
