package com.capstone.core.courtbooking.data.request;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CenterOwnerCourtCourtBookingListRequestData {
    private Long courtId;
    private LocalDate usageDate;
}
