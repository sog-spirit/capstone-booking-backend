package com.capstone.core.util.consts;

public enum CenterReviewRatingStatus {
    RECOMMEND(1),
    NOT_RECOMMEND(2);

    long value;
    private CenterReviewRatingStatus(long i) {
        this.value = i;
    }
    public long getValue() {
        return this.value;
    }
}
