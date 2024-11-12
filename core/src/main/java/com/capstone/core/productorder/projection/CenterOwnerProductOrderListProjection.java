package com.capstone.core.productorder.projection;

import java.time.LocalDateTime;

public interface CenterOwnerProductOrderListProjection {
    Long getId();
    String getUserUsername();
    LocalDateTime getCreateTimestamp();
    Long getTotal();
    String getCenterName();
    String getStatusName();
}
