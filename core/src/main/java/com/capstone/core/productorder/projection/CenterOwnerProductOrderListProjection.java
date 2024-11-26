package com.capstone.core.productorder.projection;

import java.time.LocalDateTime;

public interface CenterOwnerProductOrderListProjection {
    Long getId();
    LocalDateTime getCreateTimestamp();
    Long getTotal();

    User getUser();
    Center getCenter();
    Status getStatus();

    interface User {
        String getUsername();
    }

    interface Center {
        String getName();
    }

    interface Status {
        String getName();
    }
}
