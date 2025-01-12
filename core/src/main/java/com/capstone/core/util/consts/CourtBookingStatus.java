package com.capstone.core.util.consts;

public enum CourtBookingStatus {
    PENDING(1),
    BOOKED(2),
    UNAVAILABLE(3),
    SELECT(4),
    CANCELLED(5);

    long value;
    private CourtBookingStatus(long i) {
        this.value = i;
    }
    public Long getValue() {
        return this.value;
    }
    public static String getName(Long value) {
        for (CourtBookingStatus status : CourtBookingStatus.values()) {
            if (status.value == value) {
                if (status.toString().length() == 1) {
                    return status.toString();
                } else {
                    return status.toString().substring(0, 1).toUpperCase() + status.toString().substring(1).toLowerCase();
                }
            }
        }
        return null;
    }
}
