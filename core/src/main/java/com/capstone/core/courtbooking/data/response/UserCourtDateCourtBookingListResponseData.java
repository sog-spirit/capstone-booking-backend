package com.capstone.core.courtbooking.data.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCourtDateCourtBookingListResponseData {
    private List<Object> timeMarks;
}