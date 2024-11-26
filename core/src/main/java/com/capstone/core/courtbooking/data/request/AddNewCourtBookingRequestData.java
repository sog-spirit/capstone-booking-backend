package com.capstone.core.courtbooking.data.request;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddNewCourtBookingRequestData {
    private Long courtId;
    private Long centerId;
    private LocalDate usageDate;
    private LocalTime usageTimeStart;
    private LocalTime usageTimeEnd;
}
