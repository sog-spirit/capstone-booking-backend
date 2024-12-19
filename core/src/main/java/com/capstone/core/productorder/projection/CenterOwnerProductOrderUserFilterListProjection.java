package com.capstone.core.productorder.projection;

public interface CenterOwnerProductOrderUserFilterListProjection {
    User getUser();

    interface User {
        Long getId();
        String getUsername();
    }
}
