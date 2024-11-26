package com.capstone.core.courtbooking.data.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCenterCourtBookingListRequestData {
    private Long centerId;
    private Long courtId;
}
