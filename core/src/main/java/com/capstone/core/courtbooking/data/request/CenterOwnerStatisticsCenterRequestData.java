package com.capstone.core.courtbooking.data.request;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CenterOwnerStatisticsCenterRequestData {
    private Long centerId;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private String monthFrom;
    private String monthTo;
    private String yearFrom;
    private String yearTo;
}
