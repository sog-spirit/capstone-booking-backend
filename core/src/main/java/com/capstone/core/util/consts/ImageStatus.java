package com.capstone.core.util.consts;

public enum ImageStatus {
    ACTIVE(1),
    DELETED(2);

    long value;
    private ImageStatus(long i) {
        this.value = i;
    }
    public long getValue() {
        return this.value;
    }
}
