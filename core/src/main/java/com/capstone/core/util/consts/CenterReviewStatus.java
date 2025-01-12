package com.capstone.core.util.consts;

public enum CenterReviewStatus {
    PENDING(1),
    VERIFIED(2),
    DENIED(3),
    CANCELLED(4);

    long value;
    private CenterReviewStatus(long i) {
        this.value = i;
    }
    public long getValue() {
        return this.value;
    }
}
