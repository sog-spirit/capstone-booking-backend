package com.capstone.core.util.consts;

public enum ProductOrderStatus {
    PENDING(1);

    long value;
    private ProductOrderStatus(long i) {
        this.value = i;
    }

    public long getValue() {
        return this.value;
    }
}
