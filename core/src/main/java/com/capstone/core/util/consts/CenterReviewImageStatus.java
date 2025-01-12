package com.capstone.core.util.consts;

public enum CenterReviewImageStatus {
    ACTIVE(1),
    DELETED(2);

    long value;
    private CenterReviewImageStatus(long i) {
        this.value = i;
    }
    public long getValue() {
        return this.value;
    }
}
