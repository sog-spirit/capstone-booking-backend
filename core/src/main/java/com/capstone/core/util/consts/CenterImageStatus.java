package com.capstone.core.util.consts;

public enum CenterImageStatus {
    ACTIVE(1),
    DELETED(2);

    long value;
    private CenterImageStatus(long i) {
        this.value = i;
    }
    public long getValue() {
        return this.value;
    }
}
