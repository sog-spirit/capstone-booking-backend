package com.capstone.core.productorder.projection;

public interface CenterOwnerProductOrderCenterFilterListProjection {
    Center getCenter();

    interface Center {
        Long getId();
        String getName();
    }
}
