package com.capstone.core.util.consts;

public enum CenterStatus {
    ACTIVE(1),
    CLOSED(2);

    long value;
    private CenterStatus(long i) {
        this.value = i;
    }
    public long getValue() {
        return this.value;
    }
}
