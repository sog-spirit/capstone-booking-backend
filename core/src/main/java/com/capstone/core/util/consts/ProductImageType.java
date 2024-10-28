package com.capstone.core.util.consts;

public enum ProductImageType {
    THUMBNAIL(1);

    long value;
    private ProductImageType(long i) {
         this.value = i;
    }
    public long getValue() {
        return this.value;
    }
}
