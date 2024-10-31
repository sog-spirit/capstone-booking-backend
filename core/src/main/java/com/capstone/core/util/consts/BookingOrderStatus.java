package com.capstone.core.util.consts;

public enum BookingOrderStatus {
    PENDING(1);

    long value;
    private BookingOrderStatus(long i) {
        this.value = i;
    }
    public long getValue() {
        return this.value;
    }
}
