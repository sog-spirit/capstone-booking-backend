package com.capstone.core.util.consts;

public enum CourtStatus {
    ACTIVE(1),
    CLOSED(2);

    long value;
    private CourtStatus(long i) {
        this.value = i;
    }
    public long getValue() {
        return this.value;
    }
}
