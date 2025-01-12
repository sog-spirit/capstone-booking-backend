package com.capstone.core.util.consts;

public enum UserStatus {
    ACTIVE(1),
    DISCONTINUED(2);

    long value;
    private UserStatus(long i) {
        this.value = i;
    }
    public long getValue() {
        return this.value;
    }
}
