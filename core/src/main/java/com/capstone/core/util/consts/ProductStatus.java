package com.capstone.core.util.consts;

public enum ProductStatus {
    ACTIVE(1),
    DISCONTINUED(2);

    long value;
    private ProductStatus(long i) {
        this.value = i;
    }
    public long getValue() {
        return this.value;
    }
}
