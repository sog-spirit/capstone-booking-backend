package com.capstone.core.centerreview.projection;

public interface CenterOwnerReviewListProjection {
    Long getId();
    User getUser();
    Center getCenter();
    String getContent();

    interface User {
        String getUsername();
    }

    interface Center {
        String getName();
    }
}
