package com.capstone.core.courtbooking.data.request;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCourtCourtBookingListRequestData {
    Long courtId;
    LocalDate date;
}
