package com.capstone.core.util.consts;

public enum ProductImageStatus {
    ACTIVE(1),
    DELETED(2);

    long value;
    private ProductImageStatus(long i) {
        this.value = i;
    }
    public long getValue() {
        return this.value;
    }
}
