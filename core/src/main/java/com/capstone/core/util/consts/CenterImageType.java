package com.capstone.core.util.consts;

public enum CenterImageType {
    THUMBNAIL(1),
    SHOWCASE(2);

    long value;
    private CenterImageType(long i) {
        this.value = i;
    }
    public long getValue() {
        return this.value;
    }
}
