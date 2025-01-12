package com.capstone.core.util.consts;

public enum ProductInventoryStatus {
    ACTIVE(1),
    DISCONTINUED(2);

    long value;
    private ProductInventoryStatus(long i) {
        this.value = i;
    }
    public long getValue() {
        return this.value;
    }
}
