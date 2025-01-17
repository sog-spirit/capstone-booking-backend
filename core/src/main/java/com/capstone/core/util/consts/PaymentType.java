package com.capstone.core.util.consts;

public enum PaymentType {
    DIRECT(1),
    ONLINE(2);

    long value;
    private PaymentType(long i) {
        this.value = i;
    }
    public long getValue() {
        return this.value;
    }
}
