package com.capstone.core.centerreview.projection;

import java.time.LocalDateTime;

public interface UserCenterReviewListProjection {
    Long getId();
    String getContent();
    Center getCenter();
    Long getRating();
    LocalDateTime getCreateTimestamp();
    LocalDateTime getUpdateTimestamp();
    Long getStatus();

    interface Center {
        String getName();
    }
}
