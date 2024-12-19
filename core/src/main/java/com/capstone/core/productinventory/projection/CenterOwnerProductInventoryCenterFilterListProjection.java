package com.capstone.core.productinventory.projection;

public interface CenterOwnerProductInventoryCenterFilterListProjection {
    Center getCenter();

    interface Center {
        Long getId();
        String getName();
    }
}
