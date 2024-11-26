package com.capstone.core.courtbooking.specification.criteria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourtBookingCriteria {
    private Long userId;
    private Long centerUserId;
}
