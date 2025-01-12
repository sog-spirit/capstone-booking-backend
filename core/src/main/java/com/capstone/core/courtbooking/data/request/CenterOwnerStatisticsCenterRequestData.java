package com.capstone.core.courtbooking.data.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CenterOwnerStatisticsCenterRequestData {
    private Long centerId;
    private String frequency;
    private Long range;
}
