package com.capstone.core.centerreview.projection;

import java.time.LocalDateTime;

public interface CenterOwnerReviewListProjection {
    Long getId();
    User getUser();
    Center getCenter();
    String getContent();
    LocalDateTime getCreateTimestamp();
    LocalDateTime getUpdateTimestamp();
    Long getStatus();
    Long getRating();

    interface User {
        String getUsername();
    }

    interface Center {
        String getName();
    }
}
