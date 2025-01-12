package com.capstone.core.util.consts;

public enum CourtBookingProductOrderStatus {
    PENDING(1),
    PAID(2),
    CANCELED(3);

    long value;
    private CourtBookingProductOrderStatus(long i) {
        this.value = i;
    }
    public long getValue() {
        return this.value;
    }
}
