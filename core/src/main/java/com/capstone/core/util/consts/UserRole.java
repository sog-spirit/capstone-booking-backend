package com.capstone.core.util.consts;

public enum UserRole {
    ADMIN(1),
    USER(2),
    CENTER_OWNER(3),
    EMPLOYEE(4);

    long value;
    UserRole(long i) {
        this.value = i;
    }
    public long getValue() {
        return this.value;
    }
}
